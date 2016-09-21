package com.softteco.toolset.sample;

import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.AbstractStatusService;
import com.softteco.toolset.sample.persons.PersonsResource;
import org.restlet.routing.Router;
import org.restlet.ext.swagger.Swagger2SpecificationRestlet;
import org.restlet.ext.swagger.SwaggerSpecificationRestlet;

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
    protected void createInboundRoot(final Router router) {
        router.attach("/persons", PersonsResource.class);

        /*
          ../api/swagger.json
          ../api/api-docs
        */
        attachSwaggerSpecification1(router);
        attachSwaggerSpecification2(router);
    }

    private void attachSwaggerSpecification1(Router router) {
        SwaggerSpecificationRestlet swaggerSpecificationRestlet = new SwaggerSpecificationRestlet(this);
        swaggerSpecificationRestlet.setBasePath("http://myapp.com/api/");
        swaggerSpecificationRestlet.attach(router);
    }

    private void attachSwaggerSpecification2(Router router) {
        Swagger2SpecificationRestlet restlet = new Swagger2SpecificationRestlet(this);
        restlet.setBasePath("http://myapp.com/api/");
        restlet.attach(router);
    }
}
