package org.mskcc.cbio.maf;

import org.apache.commons.lang3.math.NumberUtils;

public class ValueTypeUtil {

    private ValueTypeUtil() {
        throw new IllegalStateException("This is a utility class. Do not instantiate.");
    }

    /**
     * Checks if a given string can be parsed as an integer.
     *
     * @param value the string to check
     * @return true if the string can be parsed as an integer, false otherwise
     */
    public static boolean isInteger(String value) {
        return NumberUtils.isCreatable(value) && NumberUtils.createNumber(value) instanceof Integer;
    }

    /**
     * Parses a given object as a float value.
     *
     * @param value the object to parse
     * @return the float value of the object, or null if the object is null
     * @throws IllegalArgumentException if the object type is not supported
     */
    public static Float toFloatValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double) {
            return NumberUtils.createNumber(value.toString()).floatValue();
        }
        if (value instanceof String) {
            return Float.parseFloat((String) value);
        }
        throw new IllegalArgumentException("Object type not supported by toFloatValue method. Value is: " + value.toString());
    }

    /**
     * Parses a given object as an integer value.
     *
     * @param value the object to parse
     * @return the integer value of the object, or null if the object is null
     * @throws IllegalArgumentException if the object type is not supported
     */
    public static Integer toIntValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer || value instanceof Float || value instanceof Long || value instanceof Double) {
            return NumberUtils.createNumber(value.toString()).intValue();
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new IllegalArgumentException("Object type not supported by toIntValue method. Value is: " + value.toString());
    }

}

