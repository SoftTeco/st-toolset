package com.softteco.toolset.xml;

import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import java.io.Writer;

/**
 *
 * @author serge
 */
public class NoTagsEscapePrintWriter extends PrettyPrintWriter {

    public NoTagsEscapePrintWriter(Writer writer) {
        super(writer);
    }

    @Override
    public String encodeNode(String name) {
        return name; // super.encodeNode(name);
    }

    @Override
    public String encodeAttribute(String name) {
        return name; // super.encodeNode(name);
    }
}
