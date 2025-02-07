package com.src.train.track.general.utils;

/**
 * The Enum Modules.
 */
public enum EnumModule {

    /** The purchases. */
    PURCHASES("Facturas Compra"),

    /** The sales. */
    SALES("Facturas Venta"),

    /** The client variation stocks. */
    CLIENT_VARIATION_STOCKS(null),

    /** The supplier delivery notes. */
    SUPPLIER_DELIVERY_NOTES("Albaranes de proveedor"),

    /** The client delivery notes. */
    CLIENT_DELIVERY_NOTES("Albaranes de cliente");

    // -------------------------------------------------------------------------

    /** The message. */
    private String message;

    /**
     * Instantiates a new enum module.
     *
     * @param message
     *            the message
     */
    private EnumModule(final String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

}
