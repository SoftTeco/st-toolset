package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.basic.DoubleConverter;

/**
 *
 * @author serge
 */
public class NullDoubleConverter extends DoubleConverter {

    @Override
    public Object fromString(final String str) {
        if (str == null || str.isEmpty()) {
            return 0d;
        }

        return Double.valueOf(str);
    }
}
