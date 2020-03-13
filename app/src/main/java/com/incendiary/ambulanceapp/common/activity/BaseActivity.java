package com.incendiary.ambulanceapp.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.esafirm.emvipi.Presenter;
import com.esafirm.emvipi.view.MvpView;
import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.dagger.DaggerFactory;
import com.incendiary.ambulanceapp.dagger.component.ActivityComponent;
import com.incendiary.ambulanceapp.dagger.component.AppComponent;
import com.incendiary.ambulanceapp.dagger.helper.ComponentReflectionInjector;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.data.events.EventLocationResolveRequest;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.EBus;
import com.incendiary.androidcommon.android.Toasts;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends RxAppCompatActivity {

    private static final int RC_LOCATION_REQUEST = 0x322;

    protected void onInjectComponent(AppComponent appComponent) {
        new ComponentReflectionInjector<>(ActivityComponent.class,
                DaggerFactory.activityComponent(this, appComponent))
                .inject(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInjectComponent(OganApp.component());
    }

    protected FragmentManager getBaseFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (enableEventBus()) {
            EBus.register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (enableEventBus()) {
            EBus.unregister(this);
        }
    }

    protected boolean enableEventBus() {
        return false;
    }

    @SuppressWarnings("unchecked")
    protected void bindPresenter(Presenter presenter, MvpView mvpView) {
        presenter.attachView(mvpView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocationResolve(EventLocationResolveRequest event) {
        try {
            event.getStatus().startResolutionForResult(this, RC_LOCATION_REQUEST);
        } catch (IntentSender.SendIntentException e) {
            ErrorHandler.handleError(e);
            Toasts.show("Cannot get location!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RC_LOCATION_REQUEST) {
            EBus.post(new EventLocationReady());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
