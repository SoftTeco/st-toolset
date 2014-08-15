package com.softteco.toolset.mail;

/**
 *
 * @author serge
 */
public interface MailTemplate {

    String getTo();

    String getSubject();

    String getBody();
}
