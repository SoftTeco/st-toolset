package com.softteco.toolset.restlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.dto.PageInfoDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.restlet.ext.guice.SelfInjectingServerResource;
import org.restlet.ext.servlet.ServletUtils;

/**
 *
 * @author serge
 */
public abstract class AbstractResource<S extends UserSession> extends SelfInjectingServerResource {

    @Inject
    private Provider<UserSession> userSessionProvider;

    @Override
    protected void doCatch(final Throwable throwable) {
        super.doCatch(throwable);
    }

    @Override
    protected void doInit() {
        super.doInit();

        getUserSession().setLang(this.getQueryValue("lang"));

        final PrincipalDto principalDto = PrincipalDto.build(getHttpServletRequest());
        getUserSession().setUsername(principalDto.username);
        getUserSession().setRoles(principalDto.roles);
    }

    protected S getUserSession() {
        return (S) userSessionProvider.get();
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ServletUtils.getRequest(this.getRequest());
    }

    protected HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    protected void removeFromSession(final String key) {
        getHttpSession().removeAttribute(key);
    }

    protected void addToSession(final String key, final Object value) {
        getHttpSession().setAttribute(key, value);
    }

    protected int getIntParam(final String name, final int defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return Integer.parseInt(param);
    }

    protected String getCurrentUsername() throws AuthorizationException {
        try {
            return getHttpServletRequest().getUserPrincipal().getName();
        } catch (NullPointerException e) {
            throw new AuthorizationException();
        }
    }

    protected String getSessionId() {
        return getHttpServletRequest().getSession().getId();
    }

    protected PageInfoDto getPageInfo() {
        final PageInfoDto dto = new PageInfoDto();
        dto.pageNumber = getIntParam("pageNumber", 0);
        dto.pageSize = getIntParam("pageSize", 20);
        return dto;
    }
}
