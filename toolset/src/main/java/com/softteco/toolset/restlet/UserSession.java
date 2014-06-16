package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public interface UserSession {

    String getUsername();

    void setUsername(String username);

    String getLang();

    void setLang(String lang);
}
