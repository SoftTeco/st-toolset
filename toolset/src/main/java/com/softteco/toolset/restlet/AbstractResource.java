package com.softteco.toolset.restlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.softteco.toolset.dto.PageInfoDto;
import com.softteco.toolset.dto.SortInfoDto;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.restlet.ext.guice.SelfInjectingServerResource;
import org.restlet.ext.servlet.ServletUtils;

/**
 *
 * @author serge
 * @param <S>
 */
public abstract class AbstractResource<S extends UserSession> extends SelfInjectingServerResource {

    private static final int MAX_NUMBER_OF_SORT_PARAMS = 10;

    @Inject
    private Provider<UserSession> userSessionProvider;

    @Override
    protected void doInit() {
        super.doInit();

        getUserSession().setLang(this.getQueryValue("lang"));

        final PrincipalDto principalDto = PrincipalDto.build(getHttpServletRequest());
        System.err.println("PRINCIPAL " + principalDto);
        if (principalDto != null) {
            System.err.println("PRINCIPAL " + principalDto.username);
            System.err.println("PRINCIPAL " + principalDto.roles);
            getUserSession().setUsername(principalDto.username);
            getUserSession().setRoles(principalDto.roles);
        }
    }

    protected final S getUserSession() {
        return (S) userSessionProvider.get();
    }

    protected final HttpServletRequest getHttpServletRequest() {
        return ServletUtils.getRequest(this.getRequest());
    }

    protected final HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    protected final void removeFromSession(final String key) {
        getHttpSession().removeAttribute(key);
    }

    protected final void addToSession(final String key, final Object value) {
        getHttpSession().setAttribute(key, value);
    }

    protected final double getDoubleParam(final String name, final Double defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return Double.parseDouble(param);
    }

    protected final boolean getBoolParam(final String name, final boolean defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(param);
    }

    protected final String getStringParam(final String name, final String defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return param;
    }

    protected final Integer getIntParam(final String name, final Integer defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return Integer.parseInt(param);
    }

    protected final Long getLongParam(final String name, final Long defaultValue) {
        final String param = getQuery().getValues(name);
        if (param == null) {
            return defaultValue;
        }

        return Long.parseLong(param);
    }

    protected final String getCurrentUsername() throws AuthorizationException {
        try {
            return getHttpServletRequest().getUserPrincipal().getName();
        } catch (NullPointerException e) {
            throw new AuthorizationException();
        }
    }

    protected final String getSessionId() {
        return getHttpServletRequest().getSession().getId();
    }

    protected PageInfoDto getPageInfo() {
        return getPageInfo(PageInfoDto.DEFAULT_PAGE, PageInfoDto.DEFAULT_PAGE_SIZE);
    }

    protected final PageInfoDto getPageInfo(final int pageNumber, final int pageSize) {
        final PageInfoDto dto = new PageInfoDto();
        dto.pageNumber = getIntParam("pageNumber", pageNumber);
        dto.pageSize = getIntParam("pageSize", pageSize);

        final List<SortInfoDto> sortInfoDtos = new ArrayList<>();
        addSortIfExists("", sortInfoDtos);
        for (int i = 1; i <= MAX_NUMBER_OF_SORT_PARAMS; i++) {
            addSortIfExists("" + i, sortInfoDtos);
        }

        if (!sortInfoDtos.isEmpty()) {
            dto.sort = sortInfoDtos.toArray(new SortInfoDto[]{});
        }
        return dto;
    }

    private void addSortIfExists(final String suffix, final List<SortInfoDto> sortInfoDtos) {
        if (getStringParam("sortParam" + suffix, null) != null) {
            final SortInfoDto sortInfoDto = new SortInfoDto();
            sortInfoDto.sortParam = getStringParam("sortParam" + suffix, null);
            sortInfoDto.sortAsc = getBoolParam("sortAsc" + suffix, true);
            sortInfoDtos.add(sortInfoDto);
        }
    }
}
