package com.incendiary.ambulanceapp.features;

import android.os.Bundle;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;
import com.incendiary.ambulanceapp.features.landing.MainMenuAct;
import com.incendiary.ambulanceapp.utils.Launcher;
import com.incendiary.androidcommon.etc.Logger;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class SplashAct extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aLong -> {
                    next();
                }, Logger::log);
    }

    private void next() {
        if (DataStore.getKeyCode() != null) {
            Launcher.launchThenFinish(this, MainMenuAct.class);
        } else {
            Launcher.launchThenFinish(this, LoginAct.class);
        }
    }
}
