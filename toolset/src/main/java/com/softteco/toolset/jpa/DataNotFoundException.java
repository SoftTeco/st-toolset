package com.softteco.toolset.jpa;

/**
 *
 * @author serge
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
