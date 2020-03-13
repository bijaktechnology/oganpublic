package com.incendiary.ambulanceapp;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.SandboxConfig;
import com.facebook.stetho.Stetho;
import com.google.firebase.iid.FirebaseInstanceId;
import com.incendiary.ambulanceapp.dagger.DaggerFactory;
import com.incendiary.ambulanceapp.dagger.component.AppComponent;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.androidcommon.AndroidCommon;
import com.incendiary.androidcommon.etc.Logger;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import rx.Completable;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class OganApp extends MultiDexApplication {

    private static final String TWITTER_KEY = "jB36CWWmzvfQNSggJL4OcFdMr";
    private static final String TWITTER_SECRET = "ndV4wcn9PtvdccOWUuwxjO1TyYdule5toS4hXpcaiDSjjiBC3l";


    private static AppComponent mComponent = DaggerFactory.appComponent();

    public static AppComponent component() {
        return mComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupAndroidCommon();
        setupStetho();
        setupFabric();
        setupFont();
        setupHawk();

        Logger.log(Log.DEBUG, "Token:" + FirebaseInstanceId.getInstance().getToken());
    }

    private void setupAndroidCommon() {
        AndroidCommon.with(this)
                .enableLogger(BuildConfig.DEBUG)
                .enableStricMode(false)
                .install();
    }

    private void setupStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }

    private void setupFabric() {
        Completable.fromAction(() -> {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

            if (BuildConfig.DEBUG) {
                Fabric.with(this,
                        new TwitterCore(authConfig),
                        new Digits.Builder().withTheme(R.style.Digits).build());
            } else {
                Fabric.with(this,
                        new Crashlytics(),
                        new TwitterCore(authConfig),
                        new Digits.Builder().withTheme(R.style.Digits).build());
            }


            Digits.logout();

            if (BuildConfig.DEBUG) {
                SandboxConfig config = new SandboxConfig(true, SandboxConfig.Mode.DEFAULT, null);
                Digits.setSandboxConfig(config);
            } else {
                Digits.disableSandbox();
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private void setupFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/avenir_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void setupHawk() {
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .buildRx()
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(DataStore::clearStoreIfNeeded)
                .subscribe();
    }
}
