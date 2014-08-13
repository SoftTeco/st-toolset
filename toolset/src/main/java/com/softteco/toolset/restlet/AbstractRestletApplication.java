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
    public synchronized void start() throws Exception {
        setStatusService(createStatusService());

        super.start();
    }

    @Override
    public final Restlet createInboundRoot() {
        final Router router = newRouter();
        createInboundRoot(router);

        for (Object each : getEnums()) {
            Class<? extends Enum> eachClass = (Class<? extends Enum>) each;
            router.attach("/" + eachClass.getSimpleName().toLowerCase(), EnumResource.build(eachClass));
        }

        return router;
    }

    protected abstract void createInboundRoot(final Router router);

    protected List getEnums() {
        return Collections.EMPTY_LIST;
    }
}
