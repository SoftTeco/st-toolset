package com.softteco.toolset.push.android;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author sergeizenevich
 */
public class ResultEntryDto implements Serializable {

    @JsonProperty(value = "message_id")
    public String messageId;
    @JsonProperty(value = "registration_id")
    public String registrationId;
    public String error;
}
