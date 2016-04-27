package com.softteco.toolset.push;

/**
 *
 * @author sergeizenevich
 */
public interface Payload {

    Object buildAndroidObject();

    String buildIOSMessage();

}
