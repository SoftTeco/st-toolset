package com.softteco.toolset;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.UserSession;
import com.softteco.toolset.xml.XmlProcessor;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.restlet.Application;
import org.restlet.ext.guice.SelfInjectingServerResourceModule;

/**
 *
 * @author serge
 */
public abstract class AbstractApplicationModule extends ServletModule {

    @Override
    protected final void configureServlets() {
        super.configureServlets();

        install(new SelfInjectingServerResourceModule());

        configurePersistModule();
        configureRestlets();
        configureProperties();
        configureUserSession();
        configureXmlProcessor();

        configureApplication();
    }

    protected void configureUserSession() {
        bind(UserSession.class).to(getUserSessionClass()).in(SessionScoped.class);
    }

    protected abstract Class<? extends UserSession> getUserSessionClass();

    protected void configureApplication() {
    }
    
    protected Class<? extends XmlProcessor> getXmlProcessor() {
        return null;
    }
    
    protected void configureXmlProcessor() {
        if (getXmlProcessor() != null) {
            bind(XmlProcessor.class).to(getXmlProcessor());
        }
    }

    protected abstract String getJpaUnitName();

    private void configurePersistModule() {
        install(new JpaPersistModule(getJpaUnitName()));
        filter("/*").through(PersistFilter.class);
    }

    protected abstract Class<? extends AbstractRestletApplication> getRestletApplication();

    private void configureRestlets() {
        bind(Application.class).to(getRestletApplication());

        final Map<String, String> params = new HashMap<String, String>();
        params.put("org.restlet.application", getRestletApplication().getName());
        bind(RestletApplicationServlet.class).in(Scopes.SINGLETON);
        serve("/api/*").with(RestletApplicationServlet.class, params);
    }

    protected String getPropertiesPath() {
        return null;
    }

    protected Properties getDefaultProperties() {
        return new Properties();
    }

    private void configureProperties() {
        if (getPropertiesPath() == null) {
            return;
        }

        try {
            final Properties properties = new Properties();
            properties.load(new FileReader(getPropertiesPath()));

            for (String eachKey : getDefaultProperties().stringPropertyNames()) {
                if (properties.containsKey(eachKey)) {
                    continue;
                }

                properties.put(eachKey, getDefaultProperties().getProperty(eachKey));
            }

            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException("Can't load config file", e);
        }
    }
}
