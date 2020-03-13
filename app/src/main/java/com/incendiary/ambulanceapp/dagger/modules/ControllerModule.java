package com.incendiary.ambulanceapp.dagger.modules;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    private final Controller controller;

    public ControllerModule(Controller controller) {
        this.controller = controller;
    }

    @Provides
    Controller provideController() {
        return controller;
    }

    @Provides PageHelper providePageHelper(){
        return PageHelper.getDefault();
    }
}
