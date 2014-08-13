package com.softteco.toolset.restlet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.Resource;
import org.restlet.resource.ServerResource;

/**
 *
 * @author serge
 */
public class ResourceBasedFinder extends Finder {

    private final ServerResource resource;

    public ResourceBasedFinder(ServerResource resource) {
        this.resource = resource;
    }

    @Override
    public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
        return resource;
    }

    @Override
    public ServerResource create(Request request, Response response) {
        return create(null, request, response);
    }
}
