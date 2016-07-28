package com.softteco.toolset.push.ios;

import com.google.inject.Provider;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

/**
 *
 * @author sergeizenevich
 */
public abstract class AbstractApnsServiceProvider implements Provider<ApnsService> {

    protected abstract boolean isProduction();

    protected abstract String getCertFilePath();

    protected abstract String getCertPassword();

    protected ApnsServiceBuilder configure(final ApnsServiceBuilder builder) {
        return builder;
    }

    private ApnsService apnsService;

    @Override
    public ApnsService get() {
        if (apnsService == null) {
            final ApnsServiceBuilder apnsServiceBuilder = new ApnsServiceBuilder().withCert(getCertFilePath(), getCertPassword());
            apnsServiceBuilder.withAppleDestination(isProduction());
            configure(apnsServiceBuilder);

            apnsService = apnsServiceBuilder.build();
        }

        return apnsService;
    }

}