package com.softteco.toolset.restlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.service.StatusService;

/**
 *
 * @author serge
 */
public class AbstractStatusService extends StatusService {

    @Override
    public Status getStatus(final Throwable throwable, final Request request, final Response response) {
        return new Status(getStatus(super.getStatus(throwable, request, response), throwable), throwable);
    }

    private Status getStatus(final Status status, final Throwable throwable) {
        if (throwable instanceof SecurityException) {
            return Status.CLIENT_ERROR_UNAUTHORIZED;
        }

        if (throwable instanceof AuthorizationException) {
            final AuthorizationException authorizationException = (AuthorizationException) throwable;
            if (authorizationException.getAuthorizationStatus() == AuthorizationStatus.NOT_LOGGED_IN) {
                return Status.CLIENT_ERROR_UNAUTHORIZED;
            } else {
                return Status.CLIENT_ERROR_FORBIDDEN;
            }
        }

        if (throwable instanceof ResourceException) {
            return getStatus(status, throwable.getCause());
        }

        final Status newStatus = getStatus(throwable);
        if (newStatus != null) {
            return newStatus;
        }
        return status;
    }

    protected Status getStatus(final Throwable throwable) {
        return null;
    }

    private String getMessage(final Throwable throwable) {
        if (throwable == null) {
            return "Server errors";
        }

        if (throwable instanceof ResourceException) {
            return getMessage(throwable.getCause());
        }

        return throwable.getMessage();
    }
    
    protected boolean showStackTrace() {
        return true;
    }

    @Override
    public Representation getRepresentation(final Status status, final Request request, final Response response) {
        try {
            final JSONObject jsono = new JSONObject();
            jsono.put("message", getMessage(status.getThrowable()));
            jsono.put("status", status.getCode());
            jsono.put("status-description", status.getDescription());
            if (showStackTrace() && status.getThrowable() != null) {
                status.getThrowable().printStackTrace(System.out);
                jsono.put("stackTrace", getStackTrace(status.getThrowable()));
            }
            if (status.getThrowable() instanceof ResourceException) {
                append(status.getThrowable().getCause(), jsono);
            } else {
                append(status.getThrowable(), jsono);
            }

            return new StringRepresentation(jsono.toString(), MediaType.APPLICATION_JSON);
        } catch (JSONException e) {
            e.printStackTrace(System.out);
            return new StringRepresentation(e.getMessage(), MediaType.APPLICATION_JSON);
        }
    }

    private String getStackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "Throwable is not defined";
        }

        if (throwable instanceof ResourceException) {
            return getStackTrace(throwable.getCause());
        }

        final StringWriter stackTraceWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTraceWriter));
        return stackTraceWriter.toString();
    }

    protected void append(final Throwable throwable, final JSONObject response) throws JSONException {
    }
}
