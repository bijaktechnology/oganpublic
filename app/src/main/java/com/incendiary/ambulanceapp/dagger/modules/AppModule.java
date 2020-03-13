package com.incendiary.ambulanceapp.dagger.modules;

import android.os.Handler;
import android.os.Looper;

import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.incendiary.ambulanceapp.BuildConfig;
import com.incendiary.ambulanceapp.dagger.PerApp;
import com.incendiary.androidcommon.android.ContextProvider;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class AppModule {

    @PerApp
    @Provides
    ReactiveLocationProvider reactiveLocationProvider() {
        return new ReactiveLocationProvider(ContextProvider.get());
    }

    @PerApp
    @Provides
    Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @PerApp
    @Provides
    FirebaseRemoteConfig provideRemoteConfig() {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        config.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build());
        return config;
    }

    @PerApp
    @Provides
    DefaultCameraModule provideCameraModule(){
        return new DefaultCameraModule();
    }
}
