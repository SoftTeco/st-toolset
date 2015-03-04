package com.softteco.toolset.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author serge
 */
public final class ListUtils {

    public static <E> List<E> copy(final List<E> source, final Executor<E> executor) {
        return copy(source, executor, null);
    }

    public static <E, A> List<E> copy(final List<E> source, final Executor<E> executor, final Keeper<E, A> keeper) {
        final List<E> target = new ArrayList<>();
        if (source == null) {
            return target;
        }
        for (E each : source) {
            final E targetEntry = executor.transform(each);
            if (targetEntry != null) {
                target.add(targetEntry);
            }

            if (keeper != null) {
                final A result = keeper.handle(each);
                if (result != null) {
                    keeper.a = result;
                }
            }
        }
        return target;
    }

    private ListUtils() {
    }

    public interface Executor<E> {

        E transform(E source);
    }

    public abstract static class Keeper<E, A> {

        private A a;

        public abstract A handle(E source);

        public final A get() {
            return a;
        }
    }
}
