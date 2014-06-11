package com.softteco.toolset;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 *
 * @author serge
 */
public abstract class AbstractApplicationInitializer extends GuiceServletContextListener {
    
    protected abstract Module[] getModules();

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(getModules());
    }
}
