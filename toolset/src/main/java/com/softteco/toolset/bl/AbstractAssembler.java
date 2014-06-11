package com.softteco.toolset.bl;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractAssembler<E, D> implements Assembler<E, D> {

    protected abstract Class<D> getDtoClass();

    @Override
    public final D assemble(final E entity) {
        final D dto;
        try {
            dto = getDtoClass().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        assemble(dto, entity);
        return dto;
    }

    public final List<D> assemble(final List<E> entities) {
        return Assembler.Utils.assemble(entities, this);
    }

    protected Comparator<D> getComparator() {
        return null;
    }
}
