package com.track.training.app.utils;

import jakarta.persistence.AttributeConverter;

/**
 * The Class AbstractEnumConverter.
 *
 * @param <E>
 *            the element type
 * @param <V>
 *            the value type
 */
public abstract class AbstractEnumConverter<E extends Enum<E> & EnumValue<V>, V> implements AttributeConverter<E, V> {

    /** The clazz. */
    private final Class<E> clazz;

    /** The ignore invalid. */
    private final boolean ignoreInvalid;

    /**
     * Instantiates a new abstract enum converter.
     *
     * @param clazz
     *            the clazz
     */
    protected AbstractEnumConverter(final Class<E> clazz) {
        this(clazz, false);
    }

    /**
     * Instantiates a new abstract enum converter.
     *
     * @param clazz
     *            the clazz
     * @param ignoreInvalid
     *            the ignore invalid
     */
    protected AbstractEnumConverter(final Class<E> clazz, final boolean ignoreInvalid) {
        this.clazz = clazz;
        this.ignoreInvalid = ignoreInvalid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V convertToDatabaseColumn(final E e) {
        return e == null ? null : e.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E convertToEntityAttribute(final V value) {
        if (value == null) {
            return null;
        }
        final E[] enums = this.clazz.getEnumConstants();
        for (final E e : enums) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        if (this.ignoreInvalid) {
            return null;
        }
        throw new UnsupportedOperationException("Invalid enum value: " + value);
    }

}
