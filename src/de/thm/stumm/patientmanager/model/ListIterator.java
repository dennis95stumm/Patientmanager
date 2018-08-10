package de.thm.stumm.patientmanager.model;

import java.util.Iterator;

/**
 * Iterator that can be used to iterate over the elements of a List.
 *
 * @param <T> The type of the items in the list.
 * @author Dennis Stumm
 */
public class ListIterator<T> implements Iterator {
    /**
     * The index pointing to the element where in the list the iterator stays currently.
     */
    private int currentIndex = 0;

    /**
     * List containing the items to iterate over.
     */
    private List<T> items;

    /**
     * Initializes the iterator by setting the values to the corresponding properties.
     *
     * @param items The list containing the items to iterate over.
     */
    ListIterator(List<T> items) {
        this.items = items;
    }

    /**
     * @return Boolean that indicates whether there is a next element in the list to iterate over or not.
     */
    @Override
    public boolean hasNext() {
        return this.currentIndex < this.items.length() && this.items.get(this.currentIndex) != null;
    }

    /**
     * @return The next element in the list.
     */
    @Override
    public T next() {
        return this.items.get(this.currentIndex++);
    }
}
