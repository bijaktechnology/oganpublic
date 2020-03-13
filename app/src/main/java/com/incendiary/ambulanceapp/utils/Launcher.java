package com.incendiary.ambulanceapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.androidcommon.android.ContextProvider;

public class Launcher {

    public static <T extends BaseActivity> void launch(Class<T> tClass) {
        Context context = ContextProvider.get();
        context.startActivity(
                new Intent(context, tClass)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    public static <T extends BaseActivity> void launch(Context context, Class<T> tClass) {
        context.startActivity(new Intent(context, tClass));
    }

    public static <T extends BaseActivity> void launchThenFinish(Activity activity, Class<T> tClass) {
        launch(activity, tClass);
        activity.finish();
    }
}
