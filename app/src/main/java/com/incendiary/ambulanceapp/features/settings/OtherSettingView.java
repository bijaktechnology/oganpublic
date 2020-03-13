package com.incendiary.ambulanceapp.features.settings;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;

public interface OtherSettingView extends MvpLoadingView, MvpErrorView {
    void setupSwitch(boolean isSwitchOn);
    void showPushNotificationStatusUpdate(boolean isSuccess);
}
