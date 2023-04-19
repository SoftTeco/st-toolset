package com.softteco.toolset.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.Transport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author serge
 */
public abstract class AbstractMailServiceBean implements MailService {

    private static final int SSL_MAIL_PORT = 465;
    private static final int MAIL_PORT = 2525;

    protected Email createEmailInstance() {
        return new HtmlEmail();
    }

    protected Email buildSimpleEmail() throws EmailException {
        final Email email = createEmailInstance();
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
        return isSSL() ? SSL_MAIL_PORT : MAIL_PORT;
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
        return "<b>Это тестовое сообщение ... :-)</b>";
    }

    @Override
    public boolean isOn() {
        return true;
    }

    @Override
    public void sendTestEmail() {
        final EmailDto dto = new EmailDto();
        dto.to = getTestEmail();
        dto.subject = getTestEmailSubject();
        dto.html = getTestEmailText();
        send(dto);
    }

    @Override
    public Map<Serializable, String> send(final List<EmailDto> emails) {
        return send(emails, null);
    }

    @Override
    public Map<Serializable, String> send(List<EmailDto> emails, Transport transport) {
        final Map<Serializable, String> results = new HashMap<>();
        for (EmailDto each : emails) {
            results.put(each.externalId, send(each, transport));
        }
        return results;
    }

    @Override
    public String send(final EmailDto dto) {
        return send(dto, null);
    }

    protected Email toEmail(final EmailDto dto) throws EmailException {
        final Email email = buildSimpleEmail();
        email.setCharset(EmailConstants.UTF_8);
        email.setSubject(dto.subject);
        email.setMsg(dto.html);
        email.addTo(dto.to);
        if (dto.ccs != null) {
            for (String each : dto.ccs) {
                if (each == null) {
                    continue;
                }
                email.addCc(each);
            }
        }
        return email;
    }

    @Override
    public String send(final EmailDto dto, final Transport transport) {
        if (!isOn()) {
            return null;
        }

        if (dto.to == null) {
            return null;
        }

        try {
            toEmail(dto).send();
            return null;
        } catch (EmailException e) {
            e.printStackTrace(System.out);
            return e.getMessage();
        }
    }
}
