package com.softteco.toolset;

import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.softteco.toolset.mail.MailService;
import com.softteco.toolset.push.PushServiceProvider;
import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.UserSession;
import com.softteco.toolset.security.AssertAuthorizedUser;
import com.softteco.toolset.security.AssertCurrentUser;
import com.softteco.toolset.security.AssertOneOfRoles;
import com.softteco.toolset.security.AssertRole;
import com.softteco.toolset.security.AssertRoles;
import com.softteco.toolset.security.AssertUser;
import com.softteco.toolset.security.SecurityInterceptor;
import com.softteco.toolset.xml.XmlProcessor;
import org.restlet.Application;
import org.restlet.ext.guice.SelfInjectingServerResourceModule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
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
        configurePushProviders();

        configureApplication();
    }

    protected Class<? extends MailService> getMailServiceClass() {
        return null;
    }

    protected Class<? extends PushServiceProvider> getAndroidPushProvider() {
        return null;
    }

    protected Class<? extends PushServiceProvider> getIosPushProvider() {
        return null;
    }

    protected void configureSecurity() {
        final SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        requestInjection(securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertRole.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertAuthorizedUser.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertRoles.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertUser.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertCurrentUser.class), securityInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AssertOneOfRoles.class), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertRoles.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertRole.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertUser.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertAuthorizedUser.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertCurrentUser.class), Matchers.any(), securityInterceptor);
        bindInterceptor(Matchers.annotatedWith(AssertOneOfRoles.class), Matchers.any(), securityInterceptor);
    }

    private void configureMailService() {
        if (getMailServiceClass() != null) {
            bind(MailService.class).to(getMailServiceClass());
        }
    }

    private void configurePushProviders() {
        if (getAndroidPushProvider() != null) {
            bind(PushServiceProvider.class).annotatedWith(Names.named("androidPushServiceBean")).to(getAndroidPushProvider());
        }

        if (getIosPushProvider() != null) {
            bind(PushServiceProvider.class).annotatedWith(Names.named("iosPushServiceBean")).to(getIosPushProvider());
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
        if (getJpaUnitName() == null) {
            return;
        }
        install(new JpaPersistModule(getJpaUnitName()));
        filter("/*").through(PersistFilter.class);
    }

    protected abstract Class<? extends AbstractRestletApplication> getRestletApplication();

    protected String getApiPrefix() {
        return "/api";
    }

    private void configureRestlets() {
        if (getRestletApplication() == null) {
            return;
        }

        bind(Application.class).to(getRestletApplication());

        final Map<String, String> params = new HashMap<>();
        params.put("org.restlet.application", getRestletApplication().getName());
        bind(RestletApplicationServlet.class).in(Scopes.SINGLETON);
        serve(getApiPrefix() + "/*").with(RestletApplicationServlet.class, params);
    }

    /**
     * Files with properties which would be injected as {@link com.google.inject.name.Named} value.
     *
     * @return absolute paths of files
     */
    protected String[] getPropertiesFiles() {
        return null;
    }

    protected String[] getResources() {
        return null;
    }

    protected Properties getDefaultProperties() {
        return new Properties();
    }

    private void configureProperties() {
        final Properties allProperties = new Properties();
        // default
        Properties properties = getDefaultProperties();
        if (properties != null && !properties.isEmpty()) {
            allProperties.putAll(properties);
        }
        // read files
        final String[] files = getPropertiesFiles();
        if (files != null) {
            for (String file : files) {
                try {
                    allProperties.putAll(loadProperties(new FileInputStream(file)));
                } catch (FileNotFoundException e) {
                    // skip it
                    System.out.println("We didn't find file: " + file);
                }
            }
        }
        // read resources
        final String[] resources = getResources();
        if (resources != null) {
            for (String each : resources) {
                allProperties.putAll(loadProperties(Thread.currentThread().getContextClassLoader().getResourceAsStream(each)));
            }
        }

        System.out.println("PROPERTIES: " + allProperties.size());
        Names.bindProperties(binder(), allProperties);
    }

    private Properties loadProperties(final InputStream in) {
        InputStreamReader propertiesFileReader = null;
        try {
            final Properties properties = new Properties();
            propertiesFileReader = new InputStreamReader(in, "UTF-8");
            properties.load(propertiesFileReader);
            return properties;
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
