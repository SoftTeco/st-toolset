package com.softteco.toolset.restlet;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author serge
 */
public abstract class AbstractUserSession implements UserSession {

    private String lang = "ru";
    private String username;
    private List<String> roles;
    
    public PrincipalDto getPrincipal() {
        final PrincipalDto dto = new PrincipalDto();
        dto.username = username;
        dto.roles = roles;
        return dto;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public void setLang(String lang) {
        if (lang == null || lang.isEmpty()) {
            return;
        }
        this.lang = lang;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        if (this.username != null && !this.username.equals(username)) {
            System.out.println("PROBLEM with setting username: " + this.username + " vs " + username + ". So cleanup session.");
            
            this.roles = null;
            cleanup();
        }
        this.username = username;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public void assertRole(final String... roles) {
        if (!hasRole(roles)) {
            throw new SecurityException("User [" + getUsername() + "] doesn't have roles: " + Arrays.toString(roles));
        }
    }

    public boolean hasRole(String... roles) {
        for (String each : roles) {
            if (this.roles.contains(each)) {
                return true;
            }
        }

        return false;
    }

    protected abstract void cleanup();

    public boolean isLoggedIn() {
        return username != null;
    }

    protected void checkOnLogin() {
        if (!isLoggedIn()) {
            cleanup();
            throw new SecurityException("User is not logged in.");
        }
    }
}
