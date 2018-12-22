package com.softteco.toolset.push;

import com.google.inject.Inject;
import com.softteco.toolset.scheduler.SchedulerManager;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created on 3.2.17.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class PushAsyncServiceBean implements PushAsyncService {

    @Inject
    private SchedulerManager schedulerManager;

    @Override
    public void sendMessage(final DeviceDto device, final Object payload) {
        sendMessage(Collections.singletonList(device), payload);
    }

    @Override
    public void sendMessage(final List<DeviceDto> device, final Object payload) {
        final JobDetail jobDetail = JobBuilder.newJob(PushSendingJob.class)
                .withIdentity("push_" + System.currentTimeMillis() + "_" + UUID.randomUUID()).build();
        jobDetail.getJobDataMap().put(PushSendingJob.DEVICES, device);
        jobDetail.getJobDataMap().put(PushSendingJob.PAYLOAD, payload);
        schedulerManager.addJob(jobDetail);
    }
}
