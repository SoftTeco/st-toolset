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
}
