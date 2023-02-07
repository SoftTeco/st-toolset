package com.softteco.toolset.dto;

import java.io.Serializable;

/**
 *
 * @author sergeizenevich
 */
public class BooleanDto implements Serializable {

    public Boolean result;

    public BooleanDto() {
    }

    public BooleanDto(final Boolean result) {
        this.result = result;
    }

}
