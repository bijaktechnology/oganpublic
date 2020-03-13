package com.incendiary.ambulanceapp.features.settings;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.BuildConfig;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;
import com.incendiary.ambulanceapp.features.comments.mycomment.MyCommentController;
import com.incendiary.ambulanceapp.features.notification.NotifController;
import com.incendiary.ambulanceapp.features.profile.ProfileController;
import com.incendiary.ambulanceapp.features.profile.bookinghistory.BookingHistoryController;
import com.incendiary.ambulanceapp.features.profile.medicalhistory.MedicalHistoryController;
import com.incendiary.ambulanceapp.features.report.bookmark.BookmarkListController;
import com.incendiary.ambulanceapp.features.report.history.ReportHistoryController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.text.Strings;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsController extends AbsController {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.settings_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.settings_txt_info) TextView txtInfo;

    @Inject SettingsListAdapter adapter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_settings;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.settings_title);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.inflateMenu(R.menu.menu_setting);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_logout:
                    logout();
                    break;
            }
            return true;
        });

        txtInfo.setText(Strings.format("%s\nVersion %s",
                ContextProvider.getString(R.string.app_name),
                BuildConfig.VERSION_NAME));

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(integer -> {
            switch (integer) {
                case 0:
                    goToProfileController();
                    break;
                case 1:
                    goToNotifController();
                    break;
                case 2:
                    goToBookmarkController();
                    break;
                case 3:
                    goToReportHistories();
                    break;
                case 4:
                    goToBookingHistory();
                    break;
                case 5:
                    goToMyComment();
                    break;
                case 6:
                    goToMedicalHistory();
                    break;
                case 7:
                    goToOtherSetting();
                    break;
            }
        });

        adapter.pushData(SettingsData.getSettings());
        adapter.notifyDataSetChanged();
    }

    private void logout() {
        LoginAct.startOver(ContextProvider.get());
    }

    private void goToReportHistories() {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportHistoryController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToMyComment() {
        getRouter().pushController(Routes.simpleTransaction(
                new MyCommentController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToOtherSetting() {
        getRouter().pushController(Routes.simpleTransaction(
                new OtherSettingController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToProfileController() {
        getRouter().pushController(Routes.simpleTransaction(
                new ProfileController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToNotifController() {
        getRouter().pushController(Routes.simpleTransaction(
                new NotifController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToBookmarkController() {
        getRouter().pushController(Routes.simpleTransaction(
                new BookmarkListController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToMedicalHistory() {
        getRouter().pushController(Routes.simpleTransaction(
                new MedicalHistoryController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToBookingHistory() {
        getRouter().pushController(Routes.simpleTransaction(
                new BookingHistoryController(),
                new HorizontalChangeHandler()
        ));
    }
}
