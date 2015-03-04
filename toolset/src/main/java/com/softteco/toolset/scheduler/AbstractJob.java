package com.softteco.toolset.scheduler;

import com.google.inject.Injector;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author serge
 */
public class AbstractJob implements Job {

    public static final String INJECTOR = "injector";

    protected final Injector getInjector(final JobExecutionContext jec) {
        return (Injector) jec.getJobDetail().getJobDataMap().get(INJECTOR);
    }

    @Override
    public void execute(final JobExecutionContext jec) throws JobExecutionException {
        final Injector injector = getInjector(jec);
        if (injector != null) {
            injector.injectMembers(this);
        }
    }
}
