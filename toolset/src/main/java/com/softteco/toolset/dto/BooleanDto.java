package com.softteco.toolset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author sergeizenevich
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BooleanDto implements Serializable {

    public Boolean result;

    public BooleanDto() {
    }

    public BooleanDto(final Boolean result) {
        this.result = result;
    }

}
