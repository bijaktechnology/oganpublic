package com.incendiary.ambulanceapp.features.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.BaseLocationAct;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.features.booking.BookingController;
import com.incendiary.ambulanceapp.features.booking.BookingUtils;
import com.incendiary.ambulanceapp.features.booking.waiting.BookingWaitController;
import com.incendiary.ambulanceapp.features.booking.waiting.BookingWaitProps;
import com.incendiary.ambulanceapp.utils.EBus;
import com.incendiary.androidcommon.android.ActivityHelper;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAct extends BaseLocationAct implements MainView {

    private static final String ARG_PROPS = "Main.Props";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.main_container) ViewGroup container;

    @Inject MainPresenter presenter;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(savedInstanceState);
        initContent();

        bindPresenter(presenter, this);
        presenter.registerDevice();
    }

    private void setupView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActivityHelper.setDisplayAsUp(this, true);

        router = Conductor.attachRouter(this, container, savedInstanceState);
    }

    @Override
    protected void onLocationReady() {
        EBus.post(EventLocationReady.create());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initContent() {
        Bundle bundle = getIntent().getExtras();
        MainProps mainProps = Preconditions.checkNotNull(bundle.getParcelable(ARG_PROPS));

        final int mapType = mainProps.getMapType();
        ActivityHelper.setTitle(this, BookingUtils.getTitle(mainProps.getMapType()));

        router.setRoot(RouterTransaction.with(
                mainProps.isWaiting()
                        ? new BookingWaitController(new BookingWaitProps(mapType, mainProps.getBookingId()))
                        : new BookingController(mapType)
        ));
        startGetLocation();
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    /* --------------------------------------------------- */
    /* > Launcher */
    /* --------------------------------------------------- */

    public static void start(Context context, int mapType) {
        context.startActivity(new Intent(context, MainAct.class)
                .putExtra(ARG_PROPS, new MainProps(mapType, false, null)));
    }

    public static void start(Context context, MainProps mainProps) {
        context.startActivity(new Intent(context, MainAct.class)
                .putExtra(ARG_PROPS, mainProps));
    }
}
