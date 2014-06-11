package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public class AuthorizationException extends Exception {

    private AuthorizationStatus authorizationStatus;

    public AuthorizationException() {
        this(AuthorizationStatus.NOT_LOGGED_IN);
    }

    public AuthorizationException(AuthorizationStatus authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public AuthorizationException(final AuthorizationStatus authorizationStatus, final String message) {
        super(message);
        this.authorizationStatus = authorizationStatus;
    }

    public AuthorizationStatus getAuthorizationStatus() {
        return authorizationStatus;
    }
}
