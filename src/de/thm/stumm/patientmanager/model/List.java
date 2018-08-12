package de.thm.stumm.patientmanager.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Abstract class for all lists in the application containing models.
 *
 * This class provides base functionality like adding, removing, persisting and loading the models in a collection.
 * Furthermore the content of the list can be persisted by calling the `persist` method. On another initialization of
 * the list the items gets loaded automatically from the file, where they were persisted.
 *
 * @param <T> The type of the items in the list.
 * @author Dennis Stumm
 */
public abstract class List<T> implements Iterable<T> {
    /**
     * Size of the array with the items.
     *
     * When adding items more than the stackSize a new array will be created with a multiple of this size.
     *
     * This prevents from creating every time a new array when adding one more element and makes the list dynamic
     * without limiting the size.
     */
    private final int stackSize = 1000;

    /**
     * Index where the next item can be inserted in the array, that contains the items.
     */
    private int currentIndex = 0;

    /**
     * Array holding all items of the list.
     */
    private Object[] items;

    /**
     * Initializes the list and loads when possible the models into the list.
     *
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException If an error gets thrown while reading the CSV-File.
     */
    public List() throws MalformedCsvLineException, IOException {
        items = new Object[this.stackSize];
        loadItems();
    }

    /**
     * Adds the passed item to the list.
     *
     * WARNING: If the method gets called twice with the same object as parameter, the object will be twice in the
     * list.
     *
     * @param item Item that should be added to the list.
     */
    public void add(T item) {
        if (this.currentIndex == this.items.length) {
            this.items = Arrays.copyOf(this.items, this.items.length + this.stackSize);
        }

        this.items[this.currentIndex++] = item;
    }

    /**
     * Searches for the passed item in the list.
     *
     * @param item The object which should be searched in the list.
     * @return The found item or null if nothing was found.
     */
    public T find(T item) {
        for (Object currentItem : this) {
            if (item.equals(currentItem)) {
                return (T) currentItem;
            }
        }

        return null;
    }

    /**
     * Searches for the first item in the list, where the passed property has the passed value.
     *
     * @param property Name of property on the object to check the value on.
     * @param value Value of the property to search the item with.
     * @return The first occurrence, where the passed property has the passed value.
     */
    public T find(String property, Object value) {
        try {
            Method method = null;
            for (Object item : this) {
                if (method == null) {
                    method = item.getClass().getDeclaredMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1));
                }

                if (method.invoke(item).equals(value)) {
                    return (T) item;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches for all items, where the passed property has the passed value.
     *
     * @param property Name of property on the object to check the value on.
     * @param value Value of the property to search the items with.
     * @return Array containing all found items.
     */
    public Object[] findAll(String property, Object value) {
        Object[] foundItems = new Object[0];

        try {
            Method method = null;
            for (Object item : this) {
                if (method == null) {
                    method = item.getClass().getDeclaredMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1));
                }

                if (method.invoke(item).equals(value)) {
                    foundItems = Arrays.copyOf(foundItems, foundItems.length + 1);
                    foundItems[foundItems.length - 1] = item;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return foundItems;
    }

    /**
     * @return Iterator which can be used to iterate over the items in this list.
     */
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) new ListIterator<>(this);
    }

    /**
     * Persists the items of this list to a CSV-File.
     *
     * @throws IOException If an error while persisting the patients occurs.
     */
    public void persist() throws IOException {
        Path filePath = getFilePath();

        if (Files.notExists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(getFilePath(), Charset.defaultCharset()));
        Iterator iterator = iterator();

        for (int i = 0; iterator.hasNext(); i++) {
            iterator.next();
            printWriter.println(getCsvLine(i));
        }

        printWriter.flush();
        printWriter.close();
    }

    /**
     * Removes the passed item from the list.
     *
     * @param item Item that should be removed.
     */
    public void remove(T item) {
        Iterator iterator = iterator();
        int index = -1;

        for (int i = 0; iterator.hasNext(); i++) {
            if (iterator.next().equals(item)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            for (int i = index; i < length() - 1; i++) {
                items[i] = items[i + 1];
                items[i + 1] = null;
            }
            currentIndex--;
        }
    }

    /**
     * Adds the item with the values parsed from the passed CSV-Formatted string.
     *
     * @param csvLine The CSV-Formatted string to get the values for the new object from.
     * @throws MalformedCsvLineException If the passed csvLine contains errors.
     */
    protected abstract void add(String csvLine) throws MalformedCsvLineException;

    /**
     * @return The path to the CSV-File where the elements of this list should be persisted.
     */
    protected abstract Path getFilePath();

    /**
     * @param index The index, for which the item should be returned.
     * @return The item on the passed index in the list. The first index is zero and the last n-1.
     */
    protected T get(int index) {
        return (T) items[index];
    }

    /**
     * @param index The index where to get the item, that should be persisted.
     * @return The line that gets persisted to the CSV-File, representing the item for the passed index.
     */
    protected abstract String getCsvLine(int index);

    /**
     * Returns the length of the list.
     *
     * WARNING: This length does not equal to the amount of items in the list. It is just a length of the list, whereby
     * some slots in the list can have the value null.
     *
     * @return The length of the list.
     */
    protected int length() {
        return this.items.length;
    }

    /**
     * Loads the models from the CSV-File into the list if the appropriate file exists.
     *
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException If an error gets thrown while reading the CSV-File.
     */
    private void loadItems() throws MalformedCsvLineException, IOException {
        if (Files.exists(getFilePath())) {
            Scanner scanner = new Scanner(getFilePath());
            while (scanner.hasNextLine()) {
                this.add(scanner.nextLine());
            }
            scanner.close();
        }
    }
}
