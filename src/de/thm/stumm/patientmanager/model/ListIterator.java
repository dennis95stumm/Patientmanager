package de.thm.stumm.patientmanager.model;

import java.util.Iterator;

/**
 * @param <T>
 * @author Dennis Stumm
 */
public class ListIterator<T> implements Iterator {
    /**
     *
     */
    private int currentIndex = 0;

    /**
     *
     */
    private List<T> items;

    /**
     * @param items
     */
    protected ListIterator(List<T> items) {
        this.items = items;
    }

    /**
     * @return
     */
    @Override
    public boolean hasNext() {
        return this.currentIndex < this.items.length() && this.items.get(this.currentIndex) != null;
    }

    /**
     * @return
     */
    @Override
    public T next() {
        return this.items.get(this.currentIndex++);
    }
}
