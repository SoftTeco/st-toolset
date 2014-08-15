package com.softteco.toolset.mail;

import com.google.inject.ProvidedBy;

/**
 *
 * @author serge
 */
@ProvidedBy(MailProcessorProvider.class)
public interface MailProcessor {

    void add(String email, String subject, String body);

    void add(MailTemplate template);

    void close();

    String getResult();
}
