package com.softteco.toolset.restlet;

import java.util.Arrays;
import java.util.List;
import org.restlet.Restlet;
import org.restlet.resource.Finder;
import org.restlet.resource.Get;

/**
 *
 * @author serge
 */
public class EnumResource<E extends Enum> extends AbstractResource<UserSession> {

    public static <A extends Enum> Restlet build(final Class<A> a) {
        return new ResourceBasedFinder(new EnumResource<A>(a));
    }
    private final Class<E> enumClass;

    public EnumResource(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    private Class<E> getEnumClass() {
//        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
//        return (Class<E>) superclass.getActualTypeArguments()[0];
        return enumClass;
    }

    @Get("json")
    public List<E> getValues() {
        return Arrays.asList(getEnumClass().getEnumConstants());
    }
}
