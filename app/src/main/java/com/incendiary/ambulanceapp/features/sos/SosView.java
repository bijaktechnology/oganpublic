package com.incendiary.ambulanceapp.features.sos;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.sos.SosType;

public interface SosView extends MvpLoadingView, MvpErrorView{
    void showSuccess(SosType sosType);
}
