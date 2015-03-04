package com.softteco.toolset.jpa;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author serge
 */
public abstract class AbstractPersistenceTest {

    @Inject
    private Provider<EntityManager> emProvider;

    public AbstractPersistenceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    protected abstract String getPersistModuleName();

    @Before
    public final void setUp() {
        final Injector injector = Guice.createInjector(new JpaPersistModule(getPersistModuleName()));
        injector.injectMembers(this);

        final PersistService persistService = injector.getInstance(PersistService.class);
        persistService.start();
    }

    @After
    public final void tearDown() {

    }

    @Test
    public final void testFindById() throws Exception {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("proxy-test");
        Assert.assertNotNull(emFactory);
        Assert.assertNotNull(emFactory.createEntityManager());

        Assert.assertNotNull(emProvider);
        Assert.assertNotNull(emProvider.get());
    }
}
