package com.softteco.toolset.restlet;

import com.softteco.toolset.dto.PageDto;
import java.util.Arrays;
import java.util.List;
import org.restlet.Restlet;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 *
 * @author serge
 * @param <E>
 */
public final class EnumResource<E extends Enum> extends AbstractResource<UserSession> {

    public static <A extends Enum> Restlet build(final Class<A> a) {
        return new ResourceBasedFinder(new EnumResource<>(a));
    }
    private final Class<E> enumClass;

    public EnumResource(final Class<E> newEnumClass) {
        this.enumClass = newEnumClass;
    }

    private Class<E> getEnumClass() {
        return enumClass;
    }

    @Get("json")
    public List<E> getValues() {
        return Arrays.asList(getEnumClass().getEnumConstants());
    }

    @Post("json")
    public PageDto<E> getValuesAsPage(final EnumTypeDto dto) {
        return new PageDto<>(null, Arrays.asList(getEnumClass().getEnumConstants()));
    }
}
