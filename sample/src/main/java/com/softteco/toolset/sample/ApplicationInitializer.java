package com.softteco.toolset.sample;

import com.google.inject.Module;
import com.softteco.toolset.AbstractApplicationInitializer;

/**
 *
 * @author serge
 */
public class ApplicationInitializer extends AbstractApplicationInitializer {

    @Override
    protected Module[] getModules() {
        return new Module[]{new ApplicationModule()};
    }
}
