package com.softteco.toolset.restlet;

import org.restlet.Restlet;
import org.restlet.ext.guice.ResourceInjectingApplication;
import org.restlet.routing.Router;

/**
 *
 * @author serge
 */
public abstract class AbstractRestletApplication extends ResourceInjectingApplication {

    protected abstract AbstractStatusService createStatusService();

    @Override
    public synchronized void start() throws Exception {
        setStatusService(createStatusService());

        super.start();
    }

    @Override
    public final Restlet createInboundRoot() {
        final Router router = newRouter();
        createInboundRoot(router);
        return router;
    }

    protected abstract void createInboundRoot(final Router router);
}
