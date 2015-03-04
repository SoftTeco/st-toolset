package com.softteco.toolset;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author serge
 */
public abstract class AbstractApplicationInitializer extends GuiceServletContextListener {

    protected abstract Module[] getModules();

    @Override
    protected final Injector getInjector() {
        return Guice.createInjector(getModules());
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        final ServletContext sc = servletContextEvent.getServletContext();
        sc.removeAttribute(Injector.class.getName());
        super.contextDestroyed(servletContextEvent);
    }

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);

        final Injector injector = (Injector) servletContextEvent.getServletContext().getAttribute(Injector.class.getName());
        init(injector);
    }

    protected void init(final Injector injector) {
    }
}
