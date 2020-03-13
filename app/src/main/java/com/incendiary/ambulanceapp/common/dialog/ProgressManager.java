package com.incendiary.ambulanceapp.common.dialog;

import android.app.ProgressDialog;

import javax.inject.Inject;

import dagger.Lazy;

public class ProgressManager {

    private final Lazy<android.app.ProgressDialog> progressDialog;

    @Inject
    public ProgressManager(Lazy<ProgressDialog> progressDialog) {
        this.progressDialog = progressDialog;
    }

    public void setCustomMessage(String customMessage) {
        this.progressDialog.get().setMessage(customMessage);
    }

    public void show(boolean isShow) {
        if (isShow) {
            progressDialog.get().show();
        } else {
            progressDialog.get().dismiss();
        }
    }
}
