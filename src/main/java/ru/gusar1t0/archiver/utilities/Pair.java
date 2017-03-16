package ru.gusar1t0.archiver.utilities;

/**
 * @author Roman Mashenkin
 * @since 15.03.2017
 */
final class Pair<E, V> {
    private E element;
    private V value;

    Pair(E element, V value) {
        this.element = element;
        this.value = value;
    }

    E getElement() {
        return element;
    }

    V getValue() {
        return value;
    }
}