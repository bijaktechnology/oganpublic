package com.incendiary.ambulanceapp.features.profile;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;

public class ProfileAct extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new ProfileController();
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public static void start(Context context) {
        context.startActivity(new Intent(context, ProfileAct.class));
    }
}
