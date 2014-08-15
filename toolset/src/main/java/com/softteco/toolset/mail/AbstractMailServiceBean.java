package com.softteco.toolset.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author serge
 */
public abstract class AbstractMailServiceBean implements MailService {

    private Email buildSimpleEmail() throws EmailException {
        Email email = new SimpleEmail();
        return configure(email);
    }

    protected abstract void configureEmail(Email email) throws EmailException;

    private Email configure(final Email email) throws EmailException {
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        configureEmail(email);
        return email;
    }

    protected String getTestEmail() {
        return null;
    }

    protected String getTestEmailSubject() {
        return "Test email";
    }

    protected String getTestEmailText() {
        return "This is a test mail ... :-)";
    }

    @Override
    public void sendTestEmail() {
        send(getTestEmail(), getTestEmailSubject(), getTestEmailText());
    }

    @Override
    public void send(String mail, String subject, String body) {
        try {
            final Email email = buildSimpleEmail();
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(mail);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace(System.out);
        }
    }
}
