package com.softteco.toolset.scheduler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 *
 * @author sergeizenevich
 */
public class GuiceJobFactory implements JobFactory {

    private static final Logger LOGGER = LogManager.getLogger(GuiceJobFactory.class.getName());
    private final Injector injector;

    @Inject
    public GuiceJobFactory(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public Job newJob(final TriggerFiredBundle triggerFiredBundle, final Scheduler schdlr) throws SchedulerException {
        // Get the job detail so we can get the job class
        final JobDetail jobDetail = triggerFiredBundle.getJobDetail();
        final Class jobClass = jobDetail.getJobClass();

        try {
            // Get a new instance of that class from Guice so we can do dependency injection
            return (Job) injector.getInstance(jobClass);
        } catch (Exception e) {
            // Something went wrong.  Print out the stack trace here so SLF4J doesn't hide it.
            LOGGER.error("Problem with building job.", e);

            // Rethrow the exception as an UnsupportedOperationException
            throw new UnsupportedOperationException(e);
        }
    }

}
