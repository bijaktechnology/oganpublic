package com.incendiary.ambulanceapp.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefUtils {
    class Key {
        static final String STATUS_FIRST_RUN = "Key.StatusFirstRun";
    }

    public static boolean getStatusFirstRun(Context ctx) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        boolean value = sharedPreferences.getBoolean(
                Key.STATUS_FIRST_RUN, false);
        return value;
    }

    public static void saveStatusFirstRun(Context activity, boolean value) {

        Log.d("SPMANAGER", "saving " + value + " for key " + Key.STATUS_FIRST_RUN);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key.STATUS_FIRST_RUN, value);
        if (editor.commit()) {
            Log.d("SPMANAGER", "commited " + value);
        } else {
            Log.d("SPMANAGER", "not commited");
        }
    }

}
