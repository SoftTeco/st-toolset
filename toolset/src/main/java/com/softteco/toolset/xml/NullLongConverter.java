package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.basic.LongConverter;

/**
 *
 * @author serge
 */
public class NullLongConverter extends LongConverter {

    @Override
    public Object fromString(final String str) {
        if (str == null || str.isEmpty()) {
            return 0l;
        }

        return super.fromString(str);
    }
}
