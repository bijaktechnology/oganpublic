package com.incendiary.ambulanceapp.features.auth.login;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;
import com.incendiary.ambulanceapp.data.DataStore;

public class LoginAct extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new LoginController();
    }

    public static void startOver(Context context) {
        DataStore.clearProfile();
        context.startActivity(new Intent(context, LoginAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }
}
