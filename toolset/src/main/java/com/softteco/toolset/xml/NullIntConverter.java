package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.basic.IntConverter;

/**
 *
 * @author serge
 */
public final class NullIntConverter extends IntConverter {

    @Override
    public Object fromString(final String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        return super.fromString(str);
    }
}
