package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.basic.LongConverter;

/**
 *
 * @author serge
 */
public final class NullLongConverter extends LongConverter {

    @Override
    public Object fromString(final String str) {
        if (str == null || str.isEmpty()) {
            return 0L;
        }

        return super.fromString(str);
    }
}
