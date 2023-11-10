package com.example.chat.util;


/**
 * Utility Class to facilitate common operations
 */
public final class Common {

    public static Long tryParseLong(String value) {
        Long result;
        try {
            result = Long.valueOf(value);
        } catch (NumberFormatException e) {
            result = null;
        }

        return result;
    }


}
