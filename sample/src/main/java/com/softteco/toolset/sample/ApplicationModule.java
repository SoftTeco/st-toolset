package com.softteco.toolset.sample;

import com.softteco.toolset.AbstractApplicationModule;
import com.softteco.toolset.restlet.AbstractRestletApplication;
import com.softteco.toolset.restlet.UserSession;

/**
 *
 * @author serge
 */
public class ApplicationModule extends AbstractApplicationModule {

    @Override
    protected String getJpaUnitName() {
        return "sample";
    }

    @Override
    protected Class<? extends AbstractRestletApplication> getRestletApplication() {
        return RestletApplication.class;
    }

    @Override
    protected Class<? extends UserSession> getUserSessionClass() {
        return UserSessionBean.class;
    }
}
