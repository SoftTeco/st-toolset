package com.softteco.toolset.push;

import com.google.inject.Inject;
import com.softteco.toolset.scheduler.AbstractJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created on 8.11.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class PushSendingJob extends AbstractJob {

    public static final String DEVICES = "DEVICES";
    public static final String PAYLOAD = "PAYLOAD";

    @Inject
    private PushService pushService;

    @Override
    public void execute(final JobExecutionContext jec) throws JobExecutionException {
        try {
            System.out.println("Push sending job start " + jec.getJobDetail().getKey());

            final JobDataMap jobDataMap = jec.getJobDetail().getJobDataMap();
            final List<DeviceDto> devices = (List<DeviceDto>) jobDataMap.get(DEVICES);
            final Object payload = jobDataMap.get(PAYLOAD);

            pushService.sendMessage(devices, payload);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Push sending job finish " + jec.getJobDetail().getKey());
        }
    }
}
