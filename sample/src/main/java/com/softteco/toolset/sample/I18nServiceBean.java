package com.softteco.toolset.sample;

import com.google.inject.Singleton;
import com.softteco.toolset.i18n.AbstractI18nService;
import com.softteco.toolset.sample.util.ApplicationUtils;

/**
 *
 */
@Singleton
public class I18nServiceBean extends AbstractI18nService {

    public static final String BUNDLE_MAIL = "mail";

    @Override
    protected String[] getBundles() {
        return new String[] {BUNDLE_MAIL};
    }

    @Override
    protected String getPropertiesPath() {
        return ApplicationUtils.getAppPath() + "i18n";
    }
}
