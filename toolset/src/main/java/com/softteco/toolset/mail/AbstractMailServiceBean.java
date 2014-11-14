package com.softteco.toolset.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author serge
 */
public abstract class AbstractMailServiceBean implements MailService {

    private static final Logger LOGGER = LogManager.getLogger(AbstractMailServiceBean.class);

    private Email buildSimpleEmail() throws EmailException {
        Email email = new SimpleEmail();
        return configure(email);
    }

    protected abstract void configureEmail(Email email) throws EmailException;

    protected String getHostName() {
        return "smtp.googlemail.com";
    }
    
    protected boolean isSSL() {
        return true;
    }
    
    protected int getSmtpPort() {
        return isSSL() ? 465 : 2525;
    }

    private Email configure(final Email email) throws EmailException {
        email.setHostName(getHostName());
        email.setSmtpPort(getSmtpPort());
        email.setSSLOnConnect(isSSL());
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
            LOGGER.error("Problem with sending email.", e);
        }
    }
}
