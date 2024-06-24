package com.softteco.toolset.mail;

import java.io.Serializable;

public abstract class AbstractEmailBuilder implements EmailBuilder {

    private String to;

    @Override
    public EmailDto build() {
        final EmailDto dto = new EmailDto();
        dto.externalId = getExternalId();
        dto.to = to == null ? getTo() : to;
        dto.ccs = getCcs();
        dto.subject = getSubject();
        dto.html = getHtml();
        return dto;
    }

    protected abstract Serializable getExternalId();

    protected abstract String getHtml();

    protected abstract String getSubject();

    protected abstract String[] getCcs();

    protected abstract String getTo();

    public void setTo(final String to) {
        this.to = to;
    }
}
