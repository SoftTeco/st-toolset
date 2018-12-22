package com.softteco.toolset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author serge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StringDto implements Serializable {

    public String string;

    public StringDto() {
    }

    public StringDto(final String string) {
        this.string = string;
    }

}
