package com.softteco.toolset.mail;

/**
 *
 * @author serge
 */
public final class OneMailProcessor implements MailProcessor {

    private final MailService mailService;
    private int count = 0;
    private String subject;
    private final StringBuilder bodyBuilder = new StringBuilder();

    public OneMailProcessor(final MailService newMailService) {
        this.mailService = newMailService;
    }

    @Override
    public void add(final String email, final String newSubject, final String body) {
        this.subject = newSubject;
        bodyBuilder.append(newSubject).append("\n").append(body).append("\n\n");
        count++;
    }

    @Override
    public void add(final MailTemplate template) {
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
