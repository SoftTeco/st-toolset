package com.softteco.toolset.restlet;

import java.util.List;

/**
 *
 * @author serge
 */
public interface UserSession {

    String getUsername();

    void setUsername(String username);

    String getLang();

    void setLang(String lang);

    List<String> getRoles();

    void setRoles(List<String> roles);
    
    void assertRole(String... roles);

    boolean isLoggedIn();
}
