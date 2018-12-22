package com.softteco.toolset.push;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created on 7.10.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
public class PushServiceBean implements PushService {

    @Inject
    @Named("iosPushServiceBean")
    private PushServiceProvider iosPushServiceBean;

    @Inject
    @Named("androidPushServiceBean")
    private PushServiceProvider androidPushServiceBean;

    @Override
    public void sendMessage(final DeviceDto device, final Object payload) {
        getProvider(device.type).sendMessage(device.token, payload);
    }

    @Override
    public void sendMessage(final List<DeviceDto> devices, final Object payload) {
        devices.stream()
                .collect(Collectors.groupingBy(DeviceDto::getType))
                .forEach((type, items) -> {
                    List<String> tokens = items.stream()
                            .filter(d -> Objects.nonNull(d.getToken()) && d.getToken().length() > 0)
                            .map(DeviceDto::getToken)
                            .collect(Collectors.toList());
                    getProvider(type).sendMessage(tokens, payload);
                });
    }

    private PushServiceProvider getProvider(final DeviceType type) {
        switch (type) {
            case IOS:
                return iosPushServiceBean;
            case ANDROID:
                return androidPushServiceBean;
            default:
                throw new IllegalArgumentException("Device type not supported!");
        }
    }
}
