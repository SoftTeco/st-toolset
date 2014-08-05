package com.softteco.toolset.xml;

import com.thoughtworks.xstream.converters.basic.DateConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author serge
 */
public class SimpleDateConverter extends DateConverter {

    private final DateFormat dateFormat;

    public SimpleDateConverter(final String pattern) {
        dateFormat = new SimpleDateFormat(pattern);
    }

    @Override
    public Object fromString(final String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace(System.out);
            return super.fromString(str);
        }
    }
}
