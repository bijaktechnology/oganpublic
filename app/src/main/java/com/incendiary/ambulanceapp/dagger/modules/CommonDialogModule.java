package com.incendiary.ambulanceapp.dagger.modules;

import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.common.dialog.ProgressDialog;
import com.incendiary.ambulanceapp.dagger.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonDialogModule {

    @PerActivity
    @Provides
    ProgressDialog provideProgressDialog() {
        return ProgressDialog.create();
    }

    @PerActivity
    @Provides
    android.app.ProgressDialog provideAndroidProgressDialog(BaseActivity activity) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(activity);
        progressDialog.setMessage("Tunggu sebentar…");
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
