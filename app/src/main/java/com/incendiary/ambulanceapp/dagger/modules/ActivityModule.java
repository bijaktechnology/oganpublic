package com.incendiary.ambulanceapp.dagger.modules;

import android.content.Context;
import android.support.v7.app.ActionBar;

import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.dagger.PerActivity;
import com.incendiary.androidcommon.android.ActivityHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @PerActivity
    @Provides
    Context provideContext() {
        return baseActivity;
    }

    @PerActivity
    @Provides
    BaseActivity provideBaseActivity() {
        return baseActivity;
    }

    @PerActivity
    @Provides
    ActionBar provideSupportActionBar() {
        return ActivityHelper.getActionBar(baseActivity);
    }
}
