package com.softteco.toolset.mail;

import java.io.Serializable;

public abstract class AbstractEmailBuilder implements EmailBuilder {
    @Override
    public EmailDto build() {
        final EmailDto dto = new EmailDto();
        dto.externalId = getExternalId();
        dto.to = getTo();
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
}
