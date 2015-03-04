package com.softteco.toolset.xml;

import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import java.io.Writer;

/**
 *
 * @author serge
 */
public final class NoTagsEscapePrintWriter extends PrettyPrintWriter {

    public NoTagsEscapePrintWriter(final Writer writer) {
        super(writer);
    }

    @Override
    public String encodeNode(final String name) {
        return name; // super.encodeNode(name);
    }

    @Override
    public String encodeAttribute(final String name) {
        return name; // super.encodeNode(name);
    }
}
