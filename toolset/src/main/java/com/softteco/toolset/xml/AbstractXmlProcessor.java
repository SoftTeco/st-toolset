package com.softteco.toolset.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author serge
 */
public abstract class AbstractXmlProcessor implements XmlProcessor {

    private final Set<Class> supportedClasses = new HashSet<>();

    protected void addSupportedClass(final Class beanClass) {
        supportedClasses.add(beanClass);
    }

    protected abstract void init();

    private XStream getXStream() {
        if (supportedClasses.isEmpty()) {
            init();
        }

        NameCoder replacer = new NoNameCoder();
        final XStream xStream = new XStream(new XppDriver(replacer)) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        xStream.registerConverter(new NullDoubleConverter());
        xStream.registerConverter(new NullLongConverter());
        xStream.processAnnotations(supportedClasses.toArray(new Class[supportedClasses.size()]));
        return xStream;
    }

    @Override
    public <E extends Serializable> E fromXml(final String xml, final Class<E> beanClass) {
        return (E) validate(getXStream().fromXML(xml));
    }

    @Override
    public <E extends Serializable> String toXml(final E bean) {
        final StringWriter sw = new StringWriter();
        final PrettyPrintWriter cwriter = new NoTagsEscapePrintWriter(sw);
        getXStream().marshal(bean, cwriter);
        return sw.toString();
    }

    private Object validate(final Object entity) {
        final List<String> errors = validate("", entity);
        if (!errors.isEmpty()) {
            throw new XmlValidationException(errors);
        }
        return entity;
    }

    private List<String> validate(final String path, final Object entity) {
        final List<String> errors = new ArrayList<>();
        for (Field each : entity.getClass().getFields()) {
            if (!Modifier.isPublic(each.getModifiers()) || Modifier.isStatic(each.getModifiers())) {
                continue;
            }

            final Object value;
            try {
                value = each.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (value == null) {
                if (!each.isAnnotationPresent(XmlOptional.class)) {
                    errors.add(path + "." + each.getName());
                }

                continue;
            }

            errors.addAll(validate(path + "." + each.getName(), value));
        }
        return errors;
    }
}
