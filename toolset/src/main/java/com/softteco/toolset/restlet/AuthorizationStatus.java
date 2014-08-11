package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public enum AuthorizationStatus {

    TOO_MANY_TRIES_TO_ENTER_PASSWORD,
    NOT_LOGGED_IN,
    INCORECT_LOGIN_OR_PASSWORD,
    DISABLED,
    UNKNOWN;

    String getMessage() {
        switch (this) {
            case TOO_MANY_TRIES_TO_ENTER_PASSWORD:
                return "Too many tries to enter password";
            case NOT_LOGGED_IN:
                return "User is not logged in";
            case INCORECT_LOGIN_OR_PASSWORD:
                return "Incorrect credentials";
            case DISABLED:
                return "User is disabled";
            case UNKNOWN:
            default:
                return null;
        }
    }
}
