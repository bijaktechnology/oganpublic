package com.incendiary.ambulanceapp.features.tours;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;

public class TourActivity extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new TourController();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    public static void start(Context context) {
        context.startActivity(
                new Intent(context, TourActivity.class)
        );
    }
}
