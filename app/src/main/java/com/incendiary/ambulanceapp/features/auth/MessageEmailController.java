package com.incendiary.ambulanceapp.features.auth;

import android.view.View;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;
import com.incendiary.androidcommon.android.ContextProvider;

public class MessageEmailController extends AbsController {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_message_email_verification;
    }

    @Override
    protected void onViewBound(View view) {
        view.findViewById(R.id.email_verification_btn_login)
                .setOnClickListener(v -> LoginAct.startOver(ContextProvider.get()));
    }
}
