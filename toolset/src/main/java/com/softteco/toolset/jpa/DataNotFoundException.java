package com.softteco.toolset.jpa;

/**
 *
 * @author serge
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(final String message) {
        super(message);
    }
}
