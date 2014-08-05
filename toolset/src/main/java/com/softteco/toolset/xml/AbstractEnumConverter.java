package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 *
 * @author serge
 */
public abstract class AbstractEnumConverter implements SingleValueConverter {

    protected abstract Class<? extends Enum> getEnumClass();

    @Override
    public boolean canConvert(final Class type) {
        return type.equals(getEnumClass());
    }

    @Override
    public String toString(final Object obj) {
        return obj.toString();
    }

    @Override
    public Object fromString(final String str) {
        return Enum.valueOf(getEnumClass(), str.toUpperCase());
    }
}
