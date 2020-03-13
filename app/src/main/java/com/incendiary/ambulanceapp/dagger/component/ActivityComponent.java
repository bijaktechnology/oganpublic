package com.incendiary.ambulanceapp.dagger.component;

import com.incendiary.ambulanceapp.dagger.PerActivity;
import com.incendiary.ambulanceapp.dagger.modules.ActivityModule;
import com.incendiary.ambulanceapp.dagger.modules.AdapterModule;
import com.incendiary.ambulanceapp.dagger.modules.CommonDialogModule;
import com.incendiary.ambulanceapp.features.main.MainAct;
import com.incendiary.ambulanceapp.features.auth.forgotpassword.ForgotPasswordAct;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;

import dagger.Component;

@PerActivity
@Component(modules = {
        AdapterModule.class,
        CommonDialogModule.class,
        ActivityModule.class
}, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(LoginAct loginAct);

    void inject(ForgotPasswordAct forgotPasswordAct);

    void inject(MainAct mainAct);

  /* --------------------------------------------------- */
  /* > Sub-module */
  /* --------------------------------------------------- */

    ControllerComponent.Builder controllerComponent();
}
