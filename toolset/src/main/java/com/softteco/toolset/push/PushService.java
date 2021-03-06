package com.softteco.toolset.push;

import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Created on 7.10.16.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
@ImplementedBy(PushServiceBean.class)
public interface PushService {

    void sendMessage(DeviceDto device, Object payload);

    void sendMessage(List<DeviceDto> device, Object payload);
}
