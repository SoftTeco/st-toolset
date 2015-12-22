package com.softteco.toolset.security.exception;

/**
 *
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Constructs a <code>ForbiddenException</code> with no detail  message.
     */
    public ForbiddenException() {
        super();
    }

    /**
     * Constructs a <code>ForbiddenException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ForbiddenException(final String s) {
        super(s);
    }

    /**
     * Creates a <code>ForbiddenException</code> with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>ForbiddenException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *              and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public ForbiddenException(final Throwable cause) {
        super(cause);
    }

}
