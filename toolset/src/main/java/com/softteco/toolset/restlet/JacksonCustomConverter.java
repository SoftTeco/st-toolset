package com.softteco.toolset.restlet;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

/**
 *
 * @author serge
 */
public final class JacksonCustomConverter extends JacksonConverter {

    private ObjectMapper mapper = null;

    public ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = createMapper();
        }
        return mapper;
    }

    private ObjectMapper createMapper() {
        final JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        return new ObjectMapper(jsonFactory);
    }

    @Override
    protected <T> JacksonRepresentation<T> create(final MediaType mediaType, final T source) {
        final JacksonRepresentation jr = new JacksonRepresentation<>(mediaType, source);
        jr.setObjectMapper(getObjectMapper());
        return jr;
    }

    @Override
    protected <T> JacksonRepresentation<T> create(final Representation source, final Class<T> objectClass) {
        final JacksonRepresentation jr = new JacksonRepresentation<>(source, objectClass);
        jr.setObjectMapper(getObjectMapper());
        return jr;
    }
}
