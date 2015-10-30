package com.softteco.toolset.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.restlet.UserSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author serge
 */
public final class SecurityInterceptor implements MethodInterceptor {

    @Inject
    private Provider<UserSession> userSessionProvider;

    @Override
    public Object invoke(final MethodInvocation mi) throws Throwable {
        System.out.println("CURRENT USER: " + userSessionProvider.get().getUsername() + " " + userSessionProvider.get().isLoggedIn());
        handleAssertAuthorizedUser(mi);
        handleAssertUser(mi);

        System.out.println("ROLES: " + (userSessionProvider.get().getRoles() == null ? null
                : Arrays.toString(userSessionProvider.get().getRoles().toArray(new String[0]))));
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

        if (!roles.isEmpty() && !userSessionProvider.get().hasRoles(roles)) {
            throw new SecurityException("User doesn't have rights on calling this method. Requires " + Arrays.toString(roles.toArray(new String[0])));
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
