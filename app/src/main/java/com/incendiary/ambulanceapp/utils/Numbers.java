package com.incendiary.ambulanceapp.utils;

public class Numbers {

    public static int safeInteger(String val, int defaultVal) {
        try {
            return Integer.valueOf(val);
        } catch (Exception e) {
            return defaultVal;
        }
    }

}
