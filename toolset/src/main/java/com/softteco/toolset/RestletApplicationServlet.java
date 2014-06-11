package com.softteco.toolset;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServerServlet;

/**
 *
 * @author serge
 */
public class RestletApplicationServlet extends ServerServlet {

    @Inject
    private Provider<Application> applicationProvider;

    @Override
    protected Application createApplication(Context parentContext) {
        return applicationProvider.get();
    }
}
