package com.softteco.toolset.xml;

import java.io.Serializable;

/**
 *
 * @author serge
 */
public interface XmlProcessor {

    <E extends Serializable> E fromXml(String xml, Class<E> beanClass);

    <E extends Serializable> String toXml(E bean);
}
