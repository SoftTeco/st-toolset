package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 *
 * @author serge
 */
public abstract class AbstractEnumConverter implements SingleValueConverter {

    protected abstract Class<? extends Enum> getEnumClass();

    @Override
    public final boolean canConvert(final Class type) {
        return type.equals(getEnumClass());
    }

    @Override
    public final String toString(final Object obj) {
        return obj.toString();
    }

    @Override
    public final Object fromString(final String str) {
        return Enum.valueOf(getEnumClass(), str.toUpperCase());
    }
}
