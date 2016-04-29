package com.softteco.toolset.push.ios;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.notnoop.apns.ApnsService;
import com.softteco.toolset.push.Payload;
import com.softteco.toolset.push.PushService;

/**
 *
 * @author sergeizenevich
 */
public class IOSPushServiceBean implements PushService {

    @Inject
    private Provider<ApnsService> apnsServiceProvider;

    @Override
    public boolean sendMessage(final String to, final Payload payload) {
        try {
            apnsServiceProvider.get().push(to, payload.buildIOSMessage());

            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

}
