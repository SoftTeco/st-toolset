package com.softteco.toolset.mail;

/**
 *
 * @author serge
 */
public class OneMailProcessor implements MailProcessor {

    private final MailService mailService;
    private int count = 0;
    private String subject;
    private StringBuilder bodyBuilder = new StringBuilder();

    public OneMailProcessor(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void add(final String email, final String subject, final String body) {
        this.subject = subject;
        bodyBuilder.append(subject).append("\n").append(body).append("\n\n");
        count++;
    }

    @Override
    public void add(MailTemplate template) {
        add(template.getTo(), template.getSubject(), template.getBody());
    }

    @Override
    public String getResult() {
        return count + " mails were handled.";
    }

    @Override
    public void close() {
        mailService.send("serge@softteco.com", subject, bodyBuilder.toString());
    }
}
