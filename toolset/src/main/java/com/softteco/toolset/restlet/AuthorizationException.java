package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public final class AuthorizationException extends Exception {

    private AuthorizationStatus authorizationStatus;

    public AuthorizationException() {
        this(AuthorizationStatus.NOT_LOGGED_IN);
    }

    public AuthorizationException(final AuthorizationStatus newAuthorizationStatus) {
        this.authorizationStatus = newAuthorizationStatus;
    }

    public AuthorizationException(final AuthorizationStatus newAuthorizationStatus, final String message) {
        super(message);
        this.authorizationStatus = newAuthorizationStatus;
    }

    public AuthorizationStatus getAuthorizationStatus() {
        return authorizationStatus;
    }

    @Override
    public String getMessage() {
        final String message = authorizationStatus.getMessage();
        if (message == null) {
            return super.getMessage();
        }
        return message;
    }
}
