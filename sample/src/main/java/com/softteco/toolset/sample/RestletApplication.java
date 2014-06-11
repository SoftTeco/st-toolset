package com.softteco.toolset.sample;

import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.AbstractStatusService;
import com.softteco.toolset.sample.persons.PersonsResource;
import org.restlet.routing.Router;

/**
 *
 * @author serge
 */
public class RestletApplication extends AbstractRestletApplication {

    @Override
    protected AbstractStatusService createStatusService() {
        return null;
    }

    @Override
    protected void createInboundRoot(Router router) {
        router.attach("/persons", PersonsResource.class);
    }
}
