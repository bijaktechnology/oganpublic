package com.incendiary.ambulanceapp.features.report;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;

public class ReportListActivity extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new ReportListController();
    }

    public static void start(Context context) {
        context.startActivity(
                new Intent(context, ReportListActivity.class)
        );
    }
}
