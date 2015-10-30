package com.softteco.toolset.restlet;

import org.restlet.data.MediaType;
import org.restlet.engine.converter.DefaultConverter;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

import java.io.IOException;

/**
 *
 */
public class DefaultCustomConverter extends DefaultConverter {

    @Override
    public float score(final Object source, final Variant target, final Resource resource) {
        if (source instanceof Number) {
            return 1.0F;
        }
        return super.score(source, target, resource);
    }

    @Override
    public Representation toRepresentation(final Object source, final Variant target, final Resource resource) throws IOException {
        if (source instanceof Long || source instanceof Number) {
            return new StringRepresentation(source + "",
                    MediaType.getMostSpecific(target.getMediaType(),
                            MediaType.TEXT_PLAIN));
        }
        return super.toRepresentation(source, target, resource);
    }
}
