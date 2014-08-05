package com.softteco.toolset.restlet;

/**
 *
 * @author serge
 */
public abstract class AbstractUserSession implements UserSession {

    private String lang = "ru";
    private String username;

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
            cleanup();
        }
        this.username = username;
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
