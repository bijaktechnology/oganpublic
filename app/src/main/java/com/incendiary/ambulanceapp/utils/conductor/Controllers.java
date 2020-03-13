package com.incendiary.ambulanceapp.utils.conductor;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bluelinelabs.conductor.Controller;

import org.greenrobot.eventbus.EventBus;

import rx.functions.Action0;
import rx.functions.Action1;

public class Controllers {

    public static void callWhenViewReady(Controller controller, Action0 action0) {
        controller.addLifecycleListener(new Controller.LifecycleListener() {
            @Override
            public void postAttach(@NonNull Controller controller, @NonNull View view) {
                action0.call();
                controller.removeLifecycleListener(this);
            }
        });
    }

    public static void findFragment(Controller controller, @IdRes int fragmentId, Action1<Fragment> onFragmentFound) {
        if (controller.getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) controller.getActivity();
            onFragmentFound.call(appCompatActivity.getSupportFragmentManager().findFragmentById(fragmentId));
        } else {
            throw new UnsupportedOperationException("Only support for AppCompatActivity for now!");
        }
    }

    public static void removeFragment(Controller controller, @IdRes int fragmentId) {
        if (controller.getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) controller.getActivity();
            Fragment fragment = appCompatActivity.getSupportFragmentManager().findFragmentById(fragmentId);
            if (fragment != null) {
                appCompatActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commitNowAllowingStateLoss();
            }
        } else {
            throw new UnsupportedOperationException("Only support for AppCompatActivity for now!");
        }
    }

    public static void bindEventBus(Controller controller) {
        EventBus.getDefault().register(controller);

        controller.addLifecycleListener(new Controller.LifecycleListener() {
            @Override
            public void preAttach(@NonNull Controller controller, @NonNull View view) {
                if (!EventBus.getDefault().isRegistered(controller)) {
                    EventBus.getDefault().register(controller);
                }
            }

            @Override
            public void postDetach(@NonNull Controller controller, @NonNull View view) {
                if (EventBus.getDefault().isRegistered(controller)) {
                    EventBus.getDefault().unregister(controller);
                }
            }
        });
    }
}
