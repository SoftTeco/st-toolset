package com.softteco.toolset.bl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author serge
 */
public interface Assembler<E, D> {

    final class Utils {

        public static <E, D> List<D> assemble(final Collection<E> entities, final Assembler<E, D> assembler, final Comparator<D> comparator) {
            final List<D> dtos = assemble(entities, assembler);
            if (comparator != null) {
                Collections.sort(dtos, comparator);
            }
            return dtos;
        }

        public static <E, D> List<D> assemble(final Collection<E> entities, final Assembler<E, D> assembler) {
            final List<D> dtos = new ArrayList<>();
            boolean sorted = false;
            for (E each : entities) {
                final D d = assembler.assemble(each);
                if (d == null) {
                    continue;
                }
                if (d instanceof Comparable) {
                    sorted = true;
                }
                dtos.add(d);
            }
            if (sorted) {
                Collections.sort((List<Comparable>) dtos);
            }
            return dtos;
        }

        public static <E, D> List<D> assemble(final Collection<E> entities, final Assembler<E, D> assembler, final int limit, final int offset) {
            final List<D> dtos = new ArrayList<>();
            int added = 0;
            int current = 0;
            for (E each : entities) {
                try {
                    if (current < offset) {
                        continue;
                    }
                } finally {
                    current++;
                }

                if (added >= limit) {
                    break;
                }

                dtos.add(assembler.assemble(each));
                added++;
            }
            return dtos;
        }

        private Utils() {
        }
    }

    D assemble(E entity);

    void assemble(D dto, E entity);
}
