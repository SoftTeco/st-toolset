package com.softteco.toolset.i18n;

import com.google.inject.Inject;
import com.softteco.toolset.restlet.UserSession;

import java.util.Map;

/**
 * The service helps to get i18n strings.
 *
 * Each file has to have following format '{bundle}_{lang}.properties'
 * where
 * 'bundle' - group of translations
 * 'lang' - language
 *
 * Possible values:
 * - plain text ( Hello dear customer )
 * - pattern ( Hello {{name}} )
 */
public abstract class AbstractI18nSessionService extends AbstractI18nService {

    @Inject
    private UserSession session;

    public String getMessage(final String bundle, final String key) {
        return getMessage(session.getLang(), bundle, key);
    }

    public String getMessage(final String bundle, final String key, final Map<String, String> params) {
        return getMessage(session.getLang(), bundle, key, params);
    }
}

