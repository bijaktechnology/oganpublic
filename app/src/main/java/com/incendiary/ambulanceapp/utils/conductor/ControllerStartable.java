package com.incendiary.ambulanceapp.utils.conductor;

import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.androidcommon.android.helper.Startable;

public class ControllerStartable extends Startable {

    public ControllerStartable(Controller o) {
        super(o);
    }

    @Override
    public void startForResult(Intent intent, int reqCode) {
        Controller controller = (Controller) get();
        controller.startActivityForResult(intent, reqCode);
    }
}
