package com.softteco.toolset.jpa;

/**
 *
 * @author sergeizenevich
 */
public class DuplicateDataException extends Exception {

    public DuplicateDataException() {
    }

    public DuplicateDataException(final String message) {
        super(message);
    }

}
