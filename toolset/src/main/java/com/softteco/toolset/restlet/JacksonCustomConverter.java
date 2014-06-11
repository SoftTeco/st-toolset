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
public class JacksonCustomConverter extends JacksonConverter {

    private ObjectMapper mapper = null;

    @Override
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        final JacksonRepresentation jr = new JacksonRepresentation<T>(mediaType, source);
        jr.setObjectMapper(getObjectMapper());
        return jr;
    }

    @Override
    protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
        final JacksonRepresentation jr = new JacksonRepresentation<T>(source, objectClass);
        jr.setObjectMapper(getObjectMapper());
        return jr;
    }

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
}
