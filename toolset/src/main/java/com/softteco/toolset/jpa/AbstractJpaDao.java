package com.softteco.toolset.jpa;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.dto.PageInfoDto;
import java.lang.reflect.ParameterizedType;
import com.softteco.toolset.dto.SortInfoDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author serge
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractJpaDao<Entity, Id> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractJpaDao.class);

    @Inject
    private Provider<EntityManager> emProvider;

    protected final Class<Entity> getEntityClass() {
        final ParameterizedType superclass;
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            superclass = (ParameterizedType) getClass().getGenericSuperclass();
        } else if (getClass().getGenericSuperclass() instanceof Class) {
            superclass = (ParameterizedType) ((Class) getClass().getGenericSuperclass()).getGenericSuperclass();
        } else {
            throw new RuntimeException("" + getClass().getGenericSuperclass());
        }
        return (Class<Entity>) superclass.getActualTypeArguments()[0];
    }

    protected String getOrderProperty() {
        return "";
    }

    protected final List<Entity> getResultList(final Query query) {
        return query.getResultList();
    }

    protected final List<Entity> getResultList(final Query query, final PageInfoDto page) {
        if (page.isPaggable()) {
            query.setMaxResults(page.pageSize + 1);
            query.setFirstResult(page.getFirst());
        }
        return query.getResultList();
    }

    protected final Entity getSingleResultWithException(final Query query) throws DataNotFoundException {
        final List<Entity> list = getResultList(query);
        if (list.isEmpty()) {
            throw new DataNotFoundException("Data was not found.");
        }
        return list.get(0);
    }

    protected final Entity getSingleResult(final Query query) {
        final List<Entity> list = getResultList(query);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    protected final EntityManager getEntityManager() {
        return emProvider.get();
    }

    public final EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    public final Entity getReference(final Id key) {
        return getEntityManager().getReference(getEntityClass(), key);
    }

    public final Entity findById(final Id key) throws DataNotFoundException {
        final Entity entity = getEntityManager().find(getEntityClass(), key);
        if (entity == null) {
            throw new DataNotFoundException("Entity " + getEntityClass() + " was not found for key: " + key);
        }
        return entity;
    }

    public final List<Entity> findAll() {
        final StringBuilder queryBuilder = new StringBuilder("select e from ").append(getEntityClass().getSimpleName()).append(" e");
        if (getOrderProperty() != null && !getOrderProperty().isEmpty()) {
            queryBuilder.append(" order by e.").append(getOrderProperty());
        }
        final Query query = getEntityManager().createQuery(queryBuilder.toString());
        return getResultList(query);
    }

    public final List<Entity> findAll(final PageInfoDto page) {
        final StringBuilder queryBuilder = new StringBuilder("select e from ").append(getEntityClass().getSimpleName()).append(" e");
        queryBuilder.append(getOrderBy(page));
        final Query query = getEntityManager().createQuery(queryBuilder.toString());
        return getResultList(query, page);
    }

    public final void persist(final Entity entity) {
        getEntityManager().persist(entity);
    }

    public final void persistAll(final List<Entity> entities) {
        for (Entity each : entities) {
            persist(each);
        }
    }

    public final Entity merge(final Entity entity) {
        return getEntityManager().merge(entity);
    }

    public final void remove(final Entity entity) {
        try {
            getEntityManager().refresh(entity);
        } catch (RuntimeException e) {
            LOGGER.error("Problem with refreshing... " + entity, e);
        }
        getEntityManager().remove(entity);
    }

    protected final String getOrderBy(final PageInfoDto page) {
        final StringBuilder queryBuilder = new StringBuilder();
        if (page.sort != null && page.sort.length > 0) {
            queryBuilder.append(" order by ");
            for (int i = 0; i < page.sort.length; i++) {
                final SortInfoDto sortParam = page.sort[i];
                if (i > 0) {
                    queryBuilder.append(",");
                }
                queryBuilder.append("e.");
                queryBuilder.append(sortParam.sortParam);
                queryBuilder.append(" ");
                queryBuilder.append(sortParam.sortAsc ? "asc" : "desc");
            }
        } else if (getOrderProperty() != null && !getOrderProperty().isEmpty()) {
            queryBuilder.append(" order by e.").append(getOrderProperty());
        }
        return queryBuilder.toString();
    }
}
