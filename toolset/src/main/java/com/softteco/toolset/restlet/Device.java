package com.softteco.toolset.restlet;

import java.io.Serializable;

/**
 * Created on 1/19/2017.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class Device implements Serializable {

    private String deviceId;
    private String token;
    private String type;

    public Device(final String deviceId, final String token, final String type) {
        this.deviceId = deviceId;
        this.token = token;
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
