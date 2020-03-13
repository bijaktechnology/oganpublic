package com.incendiary.ambulanceapp.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

public class ProgressDialog extends BaseDialogFragment {

    private static final String MESSAGE = "message";

    public static ProgressDialog create() {
        return create("");
    }

    public static ProgressDialog create(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE, message);
        ProgressDialog progressDialogFragment = new ProgressDialog();
        progressDialogFragment.setArguments(bundle);
        return progressDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(mContext);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(MESSAGE)) {
            String message = bundle.getString(MESSAGE);
            progressDialog.setMessage(!TextUtils.isEmpty(message) ? message : "Please wait...");
        }

        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

  /* --------------------------------------------------- */
  /* > Convenience */
  /* --------------------------------------------------- */

    public ProgressDialog uncancelable() {
        setCancelable(false);
        return this;
    }

    public ProgressDialog show(FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .add(this, ProgressDialog.class.getSimpleName())
                .commitAllowingStateLoss();
        return this;
    }

    public void dismiss() {
        dismissAllowingStateLoss();
    }
}
