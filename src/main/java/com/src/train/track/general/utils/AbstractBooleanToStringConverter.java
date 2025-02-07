package com.src.train.track.general.utils;

import static com.src.train.track.general.Constants.NO;
import static com.src.train.track.general.Constants.YES;

import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;

/**
 * The Class AbstractBooleanToStringConverter.
 */
public abstract class AbstractBooleanToStringConverter implements AttributeConverter<Boolean, String> {

    /** The strict. */
    private final boolean strict;

    /**
     * Instantiates a new abstract boolean to string converter.
     *
     * @param strict
     *            the strict
     */
    protected AbstractBooleanToStringConverter(final boolean strict) {
        this.strict = strict;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public String convertToDatabaseColumn(final Boolean value) {
        if (value == null) {
            return null;
        } else {
            return value.booleanValue() ? YES : NO;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public Boolean convertToEntityAttribute(final String value) {
        if (value == null) {
            return null;
        } else if (YES.equals(value) || "s".equals(value) || "1".equals(value)) {
            return true;
        } else if (NO.equals(value) || "n".equals(value) || "0".equals(value)) {
            return false;
        } else if (this.strict) {
            throw new IllegalStateException("Invalid boolean character: " + value);
        } else {
            return false;
        }
    }

}
