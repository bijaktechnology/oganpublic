package com.incendiary.ambulanceapp.features.choosecategory;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;

public class ChooseCategoryActivity extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new ChooseCategoryController();
    }

    public static void start(Context context) {
        context.startActivity(
                new Intent(context, ChooseCategoryActivity.class)
        );
    }
}
