package com.incendiary.ambulanceapp.features.landing;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;

public class MainMenuAct extends ControllerActivity {

    @Override
    protected Controller getController() {
        return new MainMenuController();
    }
}
