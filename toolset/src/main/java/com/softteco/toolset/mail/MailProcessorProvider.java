package com.softteco.toolset.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 * @author serge
 */
public class MailProcessorProvider implements Provider<MailProcessor> {

    @Inject
    private MailService mailService;

    @Override
    public final MailProcessor get() {
        return new MultiMailProcessor(mailService);
    }

}
