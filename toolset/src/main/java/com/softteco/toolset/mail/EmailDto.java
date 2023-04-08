package com.softteco.toolset.mail;

import java.io.Serializable;

public class EmailDto implements Serializable {
    public Serializable externalId;
    public String to;
    public String[] ccs;
    public String subject;
    public String html;
}
