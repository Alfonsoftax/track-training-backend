package com.track.training.app.utils;

import com.track.training.app.error.TrainTrackException;

public class GeneralException extends Exception implements TrainTrackException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5523089950436111499L;

    /**
     * Instantiates a new accounting request exception.
     */
    public GeneralException() {
        super();
    }

    /**
     * Instantiates a new accounting request exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public GeneralException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new accounting request exception.
     *
     * @param message
     *            the message
     */
    public GeneralException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new accounting request exception.
     *
     * @param cause
     *            the cause
     */
    public GeneralException(final Throwable cause) {
        super(cause);
    }

}
