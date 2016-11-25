package com.softteco.toolset.restlet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 *
 * @author serge
 */
public abstract class AbstractUserSession implements UserSession {

    private String lang = "ru";
    private String username;
    private Set<String> roles;

    public final PrincipalDto getPrincipal() {
        final PrincipalDto dto = new PrincipalDto();
        dto.username = username;
        dto.roles = roles;
        return dto;
    }

    protected abstract String getDefaultLang();
    protected abstract String[] getAvailableLangs();

    @Override
    public final String getLang() {
        if (!Arrays.asList(getAvailableLangs()).contains(lang)) {
            this.lang = getDefaultLang();
        }
        return lang;
    }

    @Override
    public final void setLang(final String newLang) {
        if (newLang == null || newLang.isEmpty()) {
            return;
        }
        this.lang = newLang;
    }

    @Override
    public final String getUsername() {
        return username;
    }

    @Override
    public final void setUsername(final String newUsername) {
        if (this.username != null && !this.username.equals(newUsername)) {
            this.roles = null;
            cleanup();
        }
        this.username = newUsername;
    }

    @Override
    public final Set<String> getRoles() {
        return roles;
    }

    @Override
    public final void setRoles(final Set<String> newRoles) {
        this.roles = newRoles;
    }

    public final void assertRole(final String roleForCheck) {
        assertRoles(roleForCheck);
    }

    public final void assertRoles(final String... rolesForCheck) {
        if (!hasRoles(rolesForCheck)) {
            throw new SecurityException("User [" + getUsername() + "] doesn't have roles: " + Arrays.toString(rolesForCheck));
        }
    }

    @Override
    public final boolean hasRole(final String roleForCheck) {
        return hasRoles(roleForCheck);
    }

    @Override
    public final boolean hasRoles(final String... rolesForCheck) {
        return hasRoles(Arrays.asList(rolesForCheck));
    }

    @Override
    public final boolean hasRoles(final List<String> rolesForCheck) {
        if (rolesForCheck == null || rolesForCheck.isEmpty()) {
            return true;
        }

        if (!isLoggedIn()) {
            throw new SecurityException("User is not logged in.");
        }

        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }

        for (String each : rolesForCheck) {
            if (this.roles.contains(each)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasOneOfRoles(final List<String> allowedRoles) {
        for (String each : getRoles()) {
            if (allowedRoles.contains(each)) {
                return true;
            }
        }

        return false;
    }

    protected abstract void cleanup();

    @Override
    public final boolean isLoggedIn() {
        return username != null;
    }

    protected final void checkOnLogin() {
        if (!isLoggedIn()) {
            this.roles = null;
            cleanup();
            throw new SecurityException("User is not logged in.");
        }
    }
}
