package com.incendiary.ambulanceapp.utils;

import android.app.Activity;

public class KeyboardUtils {

    public static void changeKeyboardConfig(Activity activity, int mode) {
        activity.getWindow().setSoftInputMode(mode);
    }

}
