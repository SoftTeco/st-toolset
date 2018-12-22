package com.softteco.toolset.mail;

/**
 *
 * @author serge
 */
public interface MailService {

    void sendTestEmail();

    void send(String email, String subject, String body);

    void send(String email, String[] ccs, String subject, String body);
}
