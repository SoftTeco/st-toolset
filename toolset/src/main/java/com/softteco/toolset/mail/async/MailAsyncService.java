package com.softteco.toolset.mail.async;

import com.google.inject.ImplementedBy;

/**
 * Created on 9.11.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
@ImplementedBy(MailAsyncServiceBean.class)
public interface MailAsyncService {

    void send(String email, String subject, String body);
}
