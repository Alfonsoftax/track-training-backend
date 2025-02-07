package com.src.train.track.general.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The Class StoreBean.
 */
public class StoreBean {

    /** The data. */
    private final Map<String, Object> data = new HashMap<>();

    /**
     * Gets the data.
     *
     * @param <T>
     *            the generic type
     * @param key
     *            the key
     * @return the data
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        return (T) this.data.get(key);
    }

    /**
     * Gets the or default.
     *
     * @param <T>
     *            the generic type
     * @param key
     *            the key
     * @param defaultValue
     *            the default value
     * @return the or default
     */
    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(final String key, final T defaultValue) {
        return (T) this.data.getOrDefault(key, defaultValue);
    }

    /**
     * Checks if is true.
     *
     * @param key
     *            the key
     * @return true, if is true
     */
    public boolean isTrue(final String key) {
        return Boolean.TRUE.equals(this.get(key));
    }

    /**
     * Checks if is true.
     *
     * @param key
     *            the key
     * @return true, if is true
     */
    public <T> boolean contains(final String key, final T item) {
        final Object value = this.get(key);
        if (value instanceof Collection) {
            return ((Collection<?>) value).contains(item);
        }
        return false;
    }

    /**
     * Puts the data.
     *
     * @param <T>
     *            the generic type
     * @param key
     *            the key
     * @param data
     *            the data
     */
    public <T> void put(final String key, final T data) {
        this.data.put(key, data);
    }

    /**
     * Compute if absent.
     *
     * @param <T>
     *            the generic type
     * @param key
     *            the key
     * @param mappingFunction
     *            the mapping function
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public <T> T computeIfAbsent(final String key, final Function<String, T> mappingFunction) {
        return (T) this.data.computeIfAbsent(key, mappingFunction);
    }

    /**
     * Removes the data.
     *
     * @param <T>
     *            the generic type
     * @param key
     *            the key
     * @return the removed data
     */
    @SuppressWarnings("unchecked")
    public <T> T remove(final String key) {
        return (T) this.data.remove(key);
    }

    /**
     * Clear data.
     */
    public void clear() {
        this.data.clear();
    }

}
