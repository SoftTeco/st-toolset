package com.softteco.toolset.push;

import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * Created on 3.2.17.
 *
 * @author Denis Kuhta
 * @since JDK1.8
 */
@ImplementedBy(PushAsyncServiceBean.class)
public interface PushAsyncService {

    void sendMessage(DeviceDto device, Object payload);

    void sendMessage(List<DeviceDto> device, Object payload);
}
