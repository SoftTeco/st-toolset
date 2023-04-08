package com.softteco.toolset.mail;

public class SimpleEmailBuilder extends AbstractEmailBuilder {
    private final String email;
    private final String subject;
    private final String body;

    public SimpleEmailBuilder(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

    @Override
    protected Long getExternalId() {
        return null;
    }

    @Override
    protected String getHtml() {
        return body;
    }

    @Override
    protected String getSubject() {
        return subject;
    }

    @Override
    protected String[] getCcs() {
        return new String[0];
    }

    @Override
    protected String getTo() {
        return email;
    }
}
