package com.incendiary.ambulanceapp.features.config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.incendiary.ambulanceapp.BuildConfig;
import com.incendiary.ambulanceapp.dagger.PerApp;

import javax.inject.Inject;

import rx.Single;

@PerApp
public class ConfigManager {

    private final String KEY_LAST_VERSION = "last_version";

    private final FirebaseRemoteConfig remoteConfig;

    @Inject
    public ConfigManager(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.remoteConfig = firebaseRemoteConfig;
    }

    public Single<Boolean> shouldUpdate() {
        return Single.fromEmitter(booleanSingleEmitter -> {
            remoteConfig.fetch()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            remoteConfig.activateFetched();
                            long lastVersion = remoteConfig.getLong(KEY_LAST_VERSION);
                            booleanSingleEmitter.onSuccess(lastVersion < BuildConfig.VERSION_CODE);
                        } else {
                            booleanSingleEmitter.onError(new IllegalStateException("Cannot connect to server"));
                        }
                    });
        });
    }
}
