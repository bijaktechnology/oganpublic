package com.incendiary.ambulanceapp.features.sos;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.sos.SosType;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.Toasts;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class SosController extends AbsController implements SosView {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject SosPresenter presenter;
    @Inject ProgressManager progressManager;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_sos;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.menu_sos);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
    }

    @OnClick({
            R.id.sos_btn_fireman,
            R.id.sos_btn_police
    })
    void onSosClick(View view) {
        animateClick(view, () -> presenter.doEmergencyCall(view.getId() == R.id.sos_btn_police
                ? SosType.POLISI
                : SosType.DAMKAR));
    }

    private void goToSosComplete() {
        getRouter().pushController(Routes.simpleTransaction(
                new SosCompleteController(),
                new HorizontalChangeHandler()
        ));
    }

    private void animateClick(View view, Action0 onComplete) {
        ViewCompat.animate(view)
                .setDuration(100)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .withEndAction(() ->
                        ViewCompat.animate(view)
                                .scaleX(1f)
                                .scaleY(1f)
                                .withEndAction(onComplete::call));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showSuccess(SosType sosType) {
        goToSosComplete();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean b, int i) {
        progressManager.show(b);
    }
}
