package com.softteco.toolset;

import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.softteco.toolset.mail.MailService;
import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.UserSession;
import com.softteco.toolset.security.AssertAuthorizedUser;
import com.softteco.toolset.security.AssertRole;
import com.softteco.toolset.security.AssertRoles;
import com.softteco.toolset.security.AssertUser;
import com.softteco.toolset.security.SecurityInterceptor;
import com.softteco.toolset.xml.XmlProcessor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
        configureMailService();
        configureSecurity();

        configureApplication();
    }

    protected Class<? extends MailService> getMailServiceClass() {
        return null;
    }

    protected void configureSecurity() {
        final SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        requestInjection(securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertRole.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertAuthorizedUser.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertRoles.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertUser.class), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertRoles.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertRole.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertUser.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertAuthorizedUser.class), Matchers.any(), securityInterceptor);
    }

    private void configureMailService() {
        if (getMailServiceClass() != null) {
            bind(MailService.class).to(getMailServiceClass());
        }
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

    protected String getApiPrefix() {
        return "/api";
    }

    private void configureRestlets() {
        bind(Application.class).to(getRestletApplication());

        final Map<String, String> params = new HashMap<>();
        params.put("org.restlet.application", getRestletApplication().getName());
        bind(RestletApplicationServlet.class).in(Scopes.SINGLETON);
        serve(getApiPrefix() + "/*").with(RestletApplicationServlet.class, params);
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

        Reader propertiesFileReader = null;
        try {
            final Properties properties = new Properties();
            propertiesFileReader = new InputStreamReader(new FileInputStream(getPropertiesPath()), "UTF-8");
            properties.load(propertiesFileReader);

            for (String eachKey : getDefaultProperties().stringPropertyNames()) {
                if (properties.containsKey(eachKey)) {
                    continue;
                }

                properties.put(eachKey, getDefaultProperties().getProperty(eachKey));
            }

            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException("Can't load config file", e);
        } finally {
            if (propertiesFileReader != null) {
                try {
                    propertiesFileReader.close();
                } catch (IOException e) {
                    throw new RuntimeException("Can't close reader", e);
                }
            }
        }
    }
}
