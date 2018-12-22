package com.softteco.toolset.push;

import java.util.List;

/**
 * @author sergeizenevich
 */
public interface PushServiceProvider {

    boolean sendMessage(String to, Object payload);

    boolean sendMessage(List<String> to, Object payload);
}
