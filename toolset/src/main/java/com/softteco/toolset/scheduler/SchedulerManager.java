package com.softteco.toolset.scheduler;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author serge
 */
@Singleton
public final class SchedulerManager {

    private static final int TWO_SECS = 2000;
    private static final int TEN_SECS = 10;
    private Scheduler scheduler;

    @Inject
    public SchedulerManager(final Injector injector) {
        try {
            final StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(injector.getInstance(GuiceJobFactory.class));
//            scheduler.clear();
            scheduler.startDelayed(TEN_SECS);
        } catch (SchedulerException e) {
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void addJob(final JobDetail jd) {
        final Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jd.getKey().getName()).
                startAt(new Date(System.currentTimeMillis() + TWO_SECS)).build();
        addJob(jd, trigger);
    }

    public void addJob(final JobDetail jd, final Trigger trigger) {
        try {
            final String jobClassName = jd.getJobClass().getName();
            System.out.println("addJob..." + jobClassName.substring(jobClassName.lastIndexOf('.') + 1));
            final Date date = scheduler.scheduleJob(jd, trigger);
            System.out.println("addJob: " + date);
        } catch (SchedulerException e) {
            e.printStackTrace(System.out);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
