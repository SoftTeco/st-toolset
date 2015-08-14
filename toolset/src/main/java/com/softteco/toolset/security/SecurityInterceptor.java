package com.softteco.toolset.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.restlet.UserSession;
import com.softteco.toolset.security.exception.ForbiddenException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author serge
 */
public final class SecurityInterceptor implements MethodInterceptor {

    @Inject
    private Provider<UserSession> userSessionProvider;

    @Override
    public Object invoke(final MethodInvocation mi) throws Throwable {
        handleAssertAuthorizedUser(mi);
        handleAssertUser(mi);
        handleAssertRoles(mi);
        return mi.proceed();
    }

    private void handleAssertRoles(final MethodInvocation mi) throws SecurityException {
        final List<String> roles = new ArrayList<>();
        if (mi.getMethod().getDeclaringClass().getAnnotation(AssertRoles.class) != null) {
            roles.addAll(Arrays.asList(mi.getMethod().getDeclaringClass().getAnnotation(AssertRoles.class).roles()));
        }
        if (mi.getMethod().getDeclaringClass().getAnnotation(AssertRole.class) != null) {
            roles.add(mi.getMethod().getDeclaringClass().getAnnotation(AssertRole.class).role());
        }
        final AssertRoles methodAssertRoles = mi.getMethod().getAnnotation(AssertRoles.class);
        final AssertRole methodAssertRole = mi.getMethod().getAnnotation(AssertRole.class);
        if (methodAssertRoles != null) {
            if (!methodAssertRoles.append()) {
                roles.clear();
            }
            roles.addAll(Arrays.asList(methodAssertRoles.roles()));
        } else if (methodAssertRole != null) {
            if (!methodAssertRole.append()) {
                roles.clear();
            }
            roles.add(methodAssertRole.role());
        }
        // current user
        Object currentUser = null;
        final AssertCurrentUser methodAssertCurrentUser = mi.getMethod().getAnnotation(AssertCurrentUser.class);
        if (methodAssertCurrentUser != null) {
            currentUser = mi.getArguments()[methodAssertCurrentUser.argument()];
            if (methodAssertCurrentUser.field() != null && !methodAssertCurrentUser.field().isEmpty()) {
                try {
                    Field f = currentUser.getClass().getDeclaredField(methodAssertCurrentUser.field());
                    f.setAccessible(true);
                    currentUser = f.get(currentUser);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // check
        if ((!roles.isEmpty() && !userSessionProvider.get().hasRoles(roles))
                && !(currentUser != null && userSessionProvider.get().getUsername().equals(currentUser.toString()))) {
            throw new ForbiddenException("User doesn't have rights on calling this method. Requires roles " + roles
                                        + (currentUser != null ? " or user must be a current" : ""));
        }
    }

    private void handleAssertUser(final MethodInvocation mi) throws SecurityException {
        final List<String> users = new ArrayList<>();
        if (mi.getMethod().getDeclaringClass().getAnnotation(AssertUser.class) != null) {
            users.add(mi.getMethod().getDeclaringClass().getAnnotation(AssertUser.class).username());
        }
        if (mi.getMethod().getAnnotation(AssertUser.class) != null) {
            users.add(mi.getMethod().getAnnotation(AssertUser.class).username());
        }
        if (!users.isEmpty() && !users.contains(userSessionProvider.get().getUsername())) {
            throw new SecurityException("User doesn't have rights on calling this method.");
        }
    }

    private void handleAssertAuthorizedUser(final MethodInvocation mi) {
        boolean sholdBeLoggedIn = false;
        if (mi.getMethod().getDeclaringClass().getAnnotation(AssertAuthorizedUser.class) != null) {
            sholdBeLoggedIn = true;
        }
        if (mi.getMethod().getAnnotation(AssertAuthorizedUser.class) != null) {
            sholdBeLoggedIn = true;
        }
        if (sholdBeLoggedIn && !userSessionProvider.get().isLoggedIn()) {
            throw new SecurityException("User is not logged in.");
        }
    }
}
