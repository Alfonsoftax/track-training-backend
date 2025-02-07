package com.src.train.track.general.domain;

import com.src.train.track.general.utils.AbstractEnumConverter;
import com.src.train.track.general.utils.EnumValue;

import jakarta.persistence.Converter;


/**
 * The Enum TransactionType.
 */
public enum TransactionType implements EnumValue<String> {

    /** The aniadir. */
    ANIADIR("A"),

    /** The borrar. */
    BORRAR("B"),

    /** The modificar. */
    MODIFICAR("M"),

    /** The ejecutar. */
    EJECUTAR("X"),

    /** No transaction. */
    NONE(null);

    /** The type. */
    private final String type;

    /**
     * Instantiates a new transaction type.
     *
     * @param type
     *            the type
     */
    TransactionType(final String type) {
        this.type = type;
    }

    /**
     * Recupera enum desde propiedad
     *
     * @param text
     *            the text
     * @return the transaction type
     */
    public static TransactionType fromTransaction(final String text) {
        if (text != null) {
            for (final TransactionType b : TransactionType.values()) {
                if (text.equals(b.type)) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return this.type;
    }

    /**
     * The Enum Converter.
     */
    @Converter(autoApply = true)
    public static class EnumConverter extends AbstractEnumConverter<TransactionType, String> {
        public EnumConverter() {
            super(TransactionType.class, true);
        }
    }
}
