package com.softteco.toolset.push;

/**
 * Created on 7.10.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class DeviceDto {

    public DeviceType type;
    public String token;

    public DeviceType getType() {
        return type;
    }

    public void setType(final DeviceType type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
