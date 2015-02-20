package com.softteco.toolset.push;

/**
 *
 * @author sergeizenevich
 */
public interface PushService {

    boolean sendMessage(String to, Object data);
}
