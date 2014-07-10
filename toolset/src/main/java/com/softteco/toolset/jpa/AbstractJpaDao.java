package com.softteco.toolset.jpa;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.dto.PageInfoDto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author serge
 */
public abstract class AbstractJpaDao<Entity, Id> {

    @Inject
    private Provider<EntityManager> emProvider;

    protected abstract Class<Entity> getEntityClass();

    protected String getOrderProperty() {
        return "";
    }

    protected List<Entity> getResultList(final Query query) {
        return query.getResultList();
    }

    protected List<Entity> getResultList(final Query query, final PageInfoDto page) {
        query.setMaxResults(page.pageSize + 1);
        query.setFirstResult(page.getFirst());
        return query.getResultList();
    }

    protected Entity getSingleResultWithException(final Query query) throws DataNotFoundException {
        final List<Entity> list = getResultList(query);
        if (list.isEmpty()) {
            throw new DataNotFoundException("Data was not found.");
        }
        return list.get(0);
    }

    protected Entity getSingleResult(final Query query) {
        final List<Entity> list = getResultList(query);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public EntityManager getEntityManager() {
        return emProvider.get();
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    public Entity getReference(final Id key) {
        return getEntityManager().getReference(getEntityClass(), key);
    }

    public Entity findById(final Id key) throws DataNotFoundException {
        final Entity entity = getEntityManager().find(getEntityClass(), key);
        if (entity == null) {
            throw new DataNotFoundException("Entity " + getEntityClass() + " was not found for key: " + key);
        }
        return entity;
    }

    public List<Entity> findAll() {
        final StringBuilder queryBuilder = new StringBuilder("select e from ").append(getEntityClass().getName()).append(" e");
        if (getOrderProperty() != null && !getOrderProperty().isEmpty()) {
            queryBuilder.append(" order by e.").append(getOrderProperty());
        }
        final Query query = getEntityManager().createQuery(queryBuilder.toString());
        return getResultList(query);
    }

    public List<Entity> findAll(final PageInfoDto page) {
        final StringBuilder queryBuilder = new StringBuilder("select e from ").append(getEntityClass().getName()).append(" e");
        if (getOrderProperty() != null && !getOrderProperty().isEmpty()) {
            queryBuilder.append(" e order by e.").append(getOrderProperty());
        }
        final Query query = getEntityManager().createQuery(queryBuilder.toString());
        return getResultList(query, page);
    }

    public void persist(final Entity entity) {
        getEntityManager().persist(entity);
    }

    public void persistAll(final List<Entity> entities) {
        for (Entity each : entities) {
            persist(each);
        }
    }

    public Entity merge(final Entity entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(final Entity entity) {
        getEntityManager().refresh(entity);
        getEntityManager().remove(entity);
    }
}
