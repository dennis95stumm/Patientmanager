package de.thm.stumm.patientmanager.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @param <T> The type of the items in the list.
 * @author Dennis Stumm
 */
public abstract class List<T> implements Iterable<T> {
    /**
     *
     */
    private final int stackSize = 1000;
    /**
     *
     */
    private int currentIndex = 0;
    /**
     *
     */
    private Object[] items;

    /**
     *
     */
    public List() {
        items = new Object[this.stackSize];
        loadItems();
    }

    /**
     * WARNING: If the method gets called twice with the same object as parameter, the object will be twice in the
     * list.
     *
     * @param item
     */
    public void add(T item) {
        if (this.currentIndex == this.items.length) {
            this.items = Arrays.copyOf(this.items, this.items.length + this.stackSize);
        }

        this.items[this.currentIndex++] = item;
    }

    /**
     * @param item
     * @return
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
     * @param property
     * @param value
     * @return
     */
    public T find(String property, Object value) {
        for (Object item : items) {
            // TODO
        }

        return null;
    }

    /**
     * @param property
     * @param value
     * @return
     */
    public List<T> findAll(String property, Object value) {
        // TODO
        return null;
    }

    /**
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) new ListIterator<>(this);
    }

    /**
     *
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
     * @param item
     */
    public void remove(T item) {
        // TODO
    }

    /**
     * @param csvLine
     */
    protected abstract void add(String csvLine);

    /**
     * @return
     */
    protected abstract Path getFilePath();

    /**
     * @param index
     * @return
     */
    protected T get(int index) {
        return (T) items[index];
    }

    /**
     * @param index
     * @return
     */
    protected abstract String getCsvLine(int index);

    /**
     * @return
     */
    protected int length() {
        return this.items.length;
    }

    /**
     *
     */
    private void loadItems() {
        if (Files.exists(getFilePath())) {
            // TODO: Read lines add call add(Line)
        }
    }
}
