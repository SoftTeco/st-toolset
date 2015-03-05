package com.softteco.toolset.push.android;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sergeizenevich
 */
public class ResponseDto implements Serializable {

    @JsonProperty(value = "multicast_id")
    public Long multicastId;
    public Long success;
    public Long failure;
    @JsonProperty(value = "canonical_ids")
    public Long canonicalIds;
    public List<ResultEntryDto> results;
}
