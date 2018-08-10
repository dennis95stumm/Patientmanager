package de.thm.stumm.patientmanager.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Abstract class for all lists in the application containing models.
 *
 * This class provides base functionality like adding, removing, persisting and loading the models in a collection.
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
     */
    public List() {
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
        for (Object currentItem : items) {
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
        for (Object item : items) {
            // TODO
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
    public T[] findAll(String property, Object value) {
        // TODO
        return null;
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
     */
    public void persist() throws IOException {
        Path filePath = getFilePath();

        if (Files.notExists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        // TODO: Iterate over the list and get and persist the csv lines!
    }

    /**
     * Removes the passed item from the list.
     *
     * @param item Item that should be removed.
     */
    public void remove(T item) {
        // TODO
    }

    /**
     * Adds the item with the values parsed from the passed CSV-Formatted string.
     *
     * @param csvLine The CSV-Formatted string to get the values for the new object from.
     */
    protected abstract void add(String csvLine);

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
     */
    private void loadItems() {
        if (Files.exists(getFilePath())) {
            // TODO: Read lines add call add(Line)
        }
    }
}
