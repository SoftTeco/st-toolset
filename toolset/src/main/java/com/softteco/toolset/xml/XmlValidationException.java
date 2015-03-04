package com.softteco.toolset.xml;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author serge
 */
public final class XmlValidationException extends RuntimeException {

    private final List<String> errors;

    public XmlValidationException(final List<String> newErrors) {
        this.errors = newErrors;
    }

    @Override
    public String getMessage() {
        return "The following fields are null, but they are required: " + Arrays.toString(errors.toArray(new String[0]));
    }
}
