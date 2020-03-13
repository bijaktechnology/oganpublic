package com.incendiary.ambulanceapp.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.dagger.HasComponent;
import com.trello.rxlifecycle.components.support.RxDialogFragment;

public class BaseDialogFragment extends RxDialogFragment {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    protected Dialog getDefaultDialog() {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
