package com.incendiary.ambulanceapp.dagger;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.dagger.component.ActivityComponent;
import com.incendiary.ambulanceapp.dagger.component.AppComponent;
import com.incendiary.ambulanceapp.dagger.component.ControllerComponent;
import com.incendiary.ambulanceapp.dagger.component.DaggerActivityComponent;
import com.incendiary.ambulanceapp.dagger.component.DaggerAppComponent;
import com.incendiary.ambulanceapp.dagger.modules.ActivityModule;
import com.incendiary.ambulanceapp.dagger.modules.CommonDialogModule;
import com.incendiary.ambulanceapp.dagger.modules.ControllerModule;
import com.incendiary.ambulanceapp.dagger.modules.NetworkModule;

public class DaggerFactory {

    public static AppComponent appComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

    public static ActivityComponent activityComponent(BaseActivity activity,
                                                      AppComponent appComponent) {

        return DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .commonDialogModule(new CommonDialogModule())
                .activityModule(new ActivityModule(activity))
                .build();
    }

    public static ControllerComponent controllerComponent(Controller controller, ActivityComponent activityComponent) {
        return activityComponent.controllerComponent()
                .controllerModule(new ControllerModule(controller))
                .build();
    }
}
