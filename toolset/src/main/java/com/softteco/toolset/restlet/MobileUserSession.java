package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public interface MobileUserSession extends UserSession {

    void setDeviceId(Long id, String deviceId);
}
