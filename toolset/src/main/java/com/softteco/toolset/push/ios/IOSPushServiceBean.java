package com.softteco.toolset.push.ios;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.softteco.toolset.push.DeviceDto;
import com.softteco.toolset.push.Payload;
import com.softteco.toolset.push.PushService;
import java.util.List;

/**
 *
 * @author sergeizenevich
 */
public class IOSPushServiceBean implements PushService {

    @Inject
    private Provider<ApnsService> apnsServiceProvider;

    public boolean sendMessage(final String to, final Payload payload) {
        try {
            final ApnsNotification notification = apnsServiceProvider.get().push(to, payload.buildIOSMessage());
            System.err.println("id: " + notification.getIdentifier());
//            System.err.println("payload: " + new String(notification.getPayload()));

            return notification.getIdentifier() > 0;
        } catch (RuntimeException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public void sendMessage(final DeviceDto device, final Object payload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessage(final List<DeviceDto> device, final Object payload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
