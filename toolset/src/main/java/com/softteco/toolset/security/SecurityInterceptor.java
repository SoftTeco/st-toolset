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
public class SecurityInterceptor implements MethodInterceptor {

    @Inject
    private Provider<UserSession> userSessionProvider;

    @Override
    public Object invoke(final MethodInvocation mi) throws Throwable {
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

        if (roles.isEmpty()) {
            throw new SecurityException("Roles are not specified. " + mi.getMethod() + " vs " + mi.getMethod().getDeclaringClass());
        }

        if (!userSessionProvider.get().hasRoles(roles)) {
            throw new SecurityException("User doesn't have rights on calling this method. Requires " + Arrays.toString(roles.toArray(new String[0])));
        }

        return mi.proceed();
    }
}
