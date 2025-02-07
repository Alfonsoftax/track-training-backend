package com.src.train.track.general.utils;

import jakarta.persistence.AttributeConverter;

public class BooleanToLongConverter implements AttributeConverter<Boolean, Long> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Long convertToDatabaseColumn(final Boolean value) {
        if (value != null) {
            return value.booleanValue() ? 1L : 0L;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean convertToEntityAttribute(final Long value) {
        if (value != null) {
            return Long.signum(value) != 0;
        }
        return false;
    }

}
