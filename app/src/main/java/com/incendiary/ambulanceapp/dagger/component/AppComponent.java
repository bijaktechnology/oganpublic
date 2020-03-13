package com.incendiary.ambulanceapp.dagger.component;

import android.os.Handler;

import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.incendiary.ambulanceapp.dagger.PerApp;
import com.incendiary.ambulanceapp.dagger.modules.AppModule;
import com.incendiary.ambulanceapp.dagger.modules.NetworkModule;
import com.incendiary.ambulanceapp.features.config.ConfigManager;
import com.incendiary.ambulanceapp.network.ApiService;

import dagger.Component;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@PerApp
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {
    ApiService apiService();
    ReactiveLocationProvider locationProvider();
    Handler mainHandler();
    ConfigManager configManager();
    DefaultCameraModule cameraModule();
}
