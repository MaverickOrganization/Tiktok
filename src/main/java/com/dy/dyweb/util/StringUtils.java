package com.dy.dyweb.util;

public class StringUtils {

    public static boolean isNotEmpty(String msg) {
        if (msg == null || msg.length() == 0) {
            return false;
        }
        return true;
    }
}
