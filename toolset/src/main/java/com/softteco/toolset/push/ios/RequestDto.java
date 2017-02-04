package com.softteco.toolset.push.ios;

import java.io.Serializable;

/**
 * Created by dkuhta on 25.5.15.
 */
class RequestDto implements Serializable {

    public Object aps;

    public RequestDto(final Object aps) {
        this.aps = aps;
    }
}
