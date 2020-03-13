package com.incendiary.ambulanceapp.features.notification;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.notification.SemarNotification;

import java.util.List;

public interface NotifView extends MvpLoadingView, MvpErrorView {
    void showContent(List<SemarNotification> notifications);
}
