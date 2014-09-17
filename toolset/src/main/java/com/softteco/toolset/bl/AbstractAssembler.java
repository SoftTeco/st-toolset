package com.softteco.toolset.bl;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractAssembler<E, D> implements Assembler<E, D> {

    protected abstract Class<D> getDtoClass();

    protected D newInstance() {
        try {
            return getDtoClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public D assemble(final E entity) {
        final D dto = newInstance();
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
