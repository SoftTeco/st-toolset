package com.softteco.toolset.sample.util;

import java.io.File;
import java.net.URL;

/**
 *
 */
public final class ApplicationUtils {

    private static String appPath;

    public static String getAppPath() {
        if (appPath == null) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = (classLoader != null) ? classLoader.getResource("/") : null;
            appPath = (url != null) ? url.getPath() : File.separator;
        }
        return appPath;
    }

    private ApplicationUtils() { }
}
