package com.softteco.toolset.bl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author serge
 */
public abstract class AutofillAbstractAssembler<E, D> extends AbstractAssembler<E, D> {

    @Override
    protected Class<D> getDtoClass() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<D>) superclass.getActualTypeArguments()[1];
    }

    protected Class<E> getEntityClass() {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) superclass.getActualTypeArguments()[0];
    }

    @Override
    public void assemble(D dto, E entity) {
        if (entity == null) {
            return;
        }

        final Class entityClass = entity.getClass();
        for (Field each : getDtoClass().getFields()) {
            try {
                final Method method;
                if (each.getType().equals(boolean.class)) {
                    method = entityClass.getMethod("is" + each.getName().substring(0, 1).toUpperCase() + each.getName().substring(1));
                } else {
                    method = entityClass.getMethod("get" + each.getName().substring(0, 1).toUpperCase() + each.getName().substring(1));
                }

                try {
                    if (method.getReturnType().equals(each.getType())) {
                        each.set(dto, method.invoke(entity));
                    } else {
                        try {
                            final Method methodInAssembler = this.getClass().getMethod("get" + each.getName().substring(0, 1).toUpperCase() + each.getName().substring(1), getEntityClass());
                            each.set(dto, methodInAssembler.invoke(this, entity));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException("Types are not equal: " + method.getReturnType().getSimpleName() + " vs " + each.getType().getSimpleName() + " " + each.getName(), e);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace(System.out);
                } catch (InvocationTargetException e) {
                    e.printStackTrace(System.out);
                }
            } catch (NoSuchMethodException e) {
                System.out.println("Skipped field " + each.getName());
            }
        }
    }

    public void disassemble(E entity, D dto) {
        final Class entityClass = entity.getClass();
        for (Field each : getDtoClass().getFields()) {
            try {
                final Method method = entityClass.getMethod("set" + each.getName().substring(0, 1).toUpperCase() + each.getName().substring(1), each.getType());

                try {
                    method.invoke(entity, each.get(dto));
                } catch (IllegalAccessException e) {
                    e.printStackTrace(System.out);
                } catch (InvocationTargetException e) {
                    e.printStackTrace(System.out);
                }
            } catch (NoSuchMethodException e) {
                System.out.println("Skipped field " + each.getName());
            }
        }
    }
}
