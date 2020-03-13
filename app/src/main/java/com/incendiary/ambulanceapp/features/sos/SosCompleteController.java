package com.incendiary.ambulanceapp.features.sos;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;

import butterknife.BindView;
import butterknife.OnClick;

public class SosCompleteController extends AbsController {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_sos_complete;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.menu_sos);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
    }

    @OnClick(R.id.sos_btn_home)
    void onHomeClick() {
        getRouter().popToRoot();
    }
}
