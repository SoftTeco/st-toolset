package com.softteco.toolset.i18n;

import com.google.inject.Inject;
import com.softteco.toolset.restlet.UserSession;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
public abstract class AbstractI18nService {

    @Inject
    private UserSession session;

    private Map<String, Map<String, Properties>> bundles;

    public AbstractI18nService() {
        final String[] bundleNames = getBundles();
        if (bundleNames == null || bundleNames.length == 0) {
            return;
        }
        bundles = new HashMap<>();
        final File folder = new File(getPropertiesPath());
        final String suffix = ".properties";
        for (String name: bundleNames) {
            final String prefix = name + "_";
            final File[] files = folder.listFiles((FilenameFilter) FileFilterUtils.and(
                    FileFilterUtils.prefixFileFilter(prefix), FileFilterUtils.suffixFileFilter(suffix)));
            Map<String, Properties> values = new HashMap<>();
            bundles.put(name, values);
            for (File file: files) {
                final String lang = file.getName().replace(prefix, "").replace(suffix, "");
                try (Reader propertiesFileReader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
                    final Properties properties = new Properties();
                    properties.load(propertiesFileReader);
                    values.put(lang, properties);
                } catch (IOException e) {
                    throw new RuntimeException("Can't load i18n file", e);
                }
            }
        }
    }

    public String getMessage(final String bundle, final String key) {
        return getMessage(session.getLang(), bundle, key);
    }

    public String getMessage(final String bundle, final String key, final Map<String, String> params) {
        return getMessage(session.getLang(), bundle, key, params);
    }

    public String getMessage(final String lang, final String bundle, final String key) {
        return getMessage(lang, bundle, key, null);
    }

    public String getMessage(final String lang, final String bundle, final String key, final Map<String, String> params) {
        String message;
        try {
            message = bundles.get(bundle).get(lang).getProperty(key);
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                    "i18n value is absent (lang: %s, bundle: %s, key: %s)", lang, bundle, key), e);
        }
        if (params != null) {
            for (Map.Entry<String, String> entry: params.entrySet()) {
                message = message.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue());
            }
        }
        return message;
    }

    protected abstract String getPropertiesPath();

    protected abstract String[] getBundles();
}
