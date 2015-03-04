package com.softteco.toolset.restlet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

/**
 *
 * @author serge
 */
public final class ResourceBasedFinder extends Finder {

    private final ServerResource resource;

    public ResourceBasedFinder(final ServerResource newResource) {
        this.resource = newResource;
    }

    @Override
    public ServerResource create(final Class<? extends ServerResource> targetClass, final Request request, final Response response) {
        return resource;
    }

    @Override
    public ServerResource create(final Request request, final Response response) {
        return create(null, request, response);
    }
}
