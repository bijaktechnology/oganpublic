package com.incendiary.ambulanceapp.utils.conductor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.internal.LifecycleHandler;

public class Conductors {

    @NonNull
    public static Router attachWithoutRebind(Activity activity, ViewGroup container, Bundle savedInstanceState) {
        LifecycleHandler lifecycleHandler = LifecycleHandler.install(activity);
        return lifecycleHandler.getRouter(container, savedInstanceState);
    }
}
