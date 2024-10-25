package com.softteco.toolset.mail;

import javax.mail.Transport;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author serge
 */
public interface MailService {

    boolean isOn();

    void sendTestEmail();

    Map<Serializable, String> send(List<EmailDto> emails);

    Map<Serializable, String> send(List<EmailDto> emails, Transport transport);

    String send(EmailDto email);

    String send(EmailDto email, Transport transport);

    String sendWithPdfAttachment(EmailDto email, byte[] pdfContent, String pdfFileName);
}
