package com.softteco.toolset.restlet;

import java.util.Collections;
import java.util.List;
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
    public final synchronized void start() throws Exception {
        final AbstractStatusService statusService = createStatusService();
        if (statusService != null) {
            setStatusService(statusService);
        }

        super.start();
    }

    @Override
    public final Restlet createInboundRoot() {
        final Router router = newRouter();
        createInboundRoot(router);

        for (Class<? extends Enum> each : getEnums()) {
            router.attach("/" + each.getSimpleName().toLowerCase(), EnumResource.build(each));
        }

        return router;
    }

    protected abstract void createInboundRoot(final Router router);

    protected List<Class<? extends Enum>> getEnums() {
        return Collections.EMPTY_LIST;
    }
}
