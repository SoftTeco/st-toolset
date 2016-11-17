package com.softteco.toolset.mail.async;

import com.google.inject.Inject;
import com.softteco.toolset.mail.MailService;
import com.softteco.toolset.scheduler.AbstractJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created on 8.11.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class MailSendingJob extends AbstractJob {

    public static final String EMAIL = "EMAIL";
    public static final String SUBJECT = "SUBJECT";
    public static final String BODY = "BODY";

    @Inject
    private MailService mailService;

    @Override
    public void execute(final JobExecutionContext jec) throws JobExecutionException {
        try {
            System.out.println("Mail sending job start " + jec.getJobDetail().getKey());

            final JobDataMap jobDataMap = jec.getJobDetail().getJobDataMap();
            final String email = (String) jobDataMap.get(EMAIL);
            final String subject = (String) jobDataMap.get(SUBJECT);
            final String body = (String) jobDataMap.get(BODY);

            mailService.send(email, subject, body);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Mail sending job finish " + jec.getJobDetail().getKey());
        }
    }
}
