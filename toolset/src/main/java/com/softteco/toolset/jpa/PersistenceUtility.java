package com.softteco.toolset.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author serge
 */
public class PersistenceUtility {

    private final String unitname;

    public PersistenceUtility(final String unitname) {
        this.unitname = unitname;
    }

    private EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory(unitname);
    }

    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
}
