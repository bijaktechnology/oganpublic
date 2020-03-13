package com.incendiary.ambulanceapp.utils;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import com.incendiary.androidcommon.android.ContextProvider;

public class AppUtils {

    public static void goToStore() {
        final String appPackageName = ContextProvider.get().getPackageName();
        try {
            ContextProvider.get()
                    .startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (ActivityNotFoundException anfe) {
            ContextProvider.get()
                    .startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
