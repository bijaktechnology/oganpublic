package com.incendiary.ambulanceapp.dagger.component;

import com.incendiary.ambulanceapp.dagger.modules.ControllerModule;
import com.incendiary.ambulanceapp.features.auth.login.LoginController;
import com.incendiary.ambulanceapp.features.auth.register.RegisterController;
import com.incendiary.ambulanceapp.features.authconfirm.KtpConfirmationController;
import com.incendiary.ambulanceapp.features.booking.BookingController;
import com.incendiary.ambulanceapp.features.booking.patientdata.PatientDataController;
import com.incendiary.ambulanceapp.features.booking.patientdata.familypicker.FamilyPickerController;
import com.incendiary.ambulanceapp.features.booking.waiting.BookingWaitController;
import com.incendiary.ambulanceapp.features.choosecategory.ChooseCategoryController;
import com.incendiary.ambulanceapp.features.comments.reportcomment.ReportCommentController;
import com.incendiary.ambulanceapp.features.comments.mycomment.MyCommentController;
import com.incendiary.ambulanceapp.features.landing.MainMenuController;
import com.incendiary.ambulanceapp.features.map.ReportMapDetailController;
import com.incendiary.ambulanceapp.features.notification.NotifController;
import com.incendiary.ambulanceapp.features.profile.ProfileController;
import com.incendiary.ambulanceapp.features.profile.addfamily.AddFamilyController;
import com.incendiary.ambulanceapp.features.profile.bookinghistory.BookingHistoryController;
import com.incendiary.ambulanceapp.features.profile.familylist.FamilyListController;
import com.incendiary.ambulanceapp.features.profile.medicalhistory.MedicalHistoryController;
import com.incendiary.ambulanceapp.features.report.ReportListController;
import com.incendiary.ambulanceapp.features.report.bookmark.BookmarkListController;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailController;
import com.incendiary.ambulanceapp.features.report.history.ReportHistoryController;
import com.incendiary.ambulanceapp.features.report.input.ReportInputController;
import com.incendiary.ambulanceapp.features.report.inputmap.ReportInputMapController;
import com.incendiary.ambulanceapp.features.report.reportfeedback.ReportFeedbackController;
import com.incendiary.ambulanceapp.features.settings.OtherSettingController;
import com.incendiary.ambulanceapp.features.settings.SettingsController;
import com.incendiary.ambulanceapp.features.sos.SosController;
import com.incendiary.ambulanceapp.features.tours.TourDetailController;
import com.incendiary.ambulanceapp.features.tours.TourController;
import com.incendiary.ambulanceapp.features.tours.list.TourListController;
import com.incendiary.ambulanceapp.features.tours.search.TourSearchController;

import dagger.Subcomponent;

@Subcomponent(modules = ControllerModule.class)
public interface ControllerComponent {

    @Subcomponent.Builder
    interface Builder {
        Builder controllerModule(ControllerModule module);
        ControllerComponent build();
    }

    /* --------------------------------------------------- */
    /* > Inject */
    /* --------------------------------------------------- */

    void inject(RegisterController registerController);
    void inject(LoginController loginController);
    void inject(KtpConfirmationController ktpConfirmationController);

    void inject(MainMenuController mainMenuController);

    void inject(BookingController bookingController);
    void inject(BookingWaitController bookingWaitController);
    void inject(PatientDataController patientDataController);

    void inject(TourController toursController);
    void inject(TourListController toursListController);
    void inject(TourDetailController tourDetailController);
    void inject(TourSearchController toursSearchController);

    void inject(SettingsController settingsController);
    void inject(OtherSettingController otherSettingController);
    void inject(BookingHistoryController bookingHistoryController);
    void inject(MedicalHistoryController medicalHistoryController);

    void inject(SosController sosController);

    void inject(ProfileController profileController);
    void inject(FamilyListController familyListController);
    void inject(AddFamilyController addFamilyController);
    void inject(FamilyPickerController familyPickerController);

    void inject(NotifController notifController);

    void inject(ReportListController reportListController);
    void inject(BookmarkListController bookmarkListController);
    void inject(ReportDetailController reportDetailController);
    void inject(ReportInputController reportInputController);
    void inject(ReportFeedbackController reportFeedbackController);
    void inject(ReportHistoryController reportHistoryController);
    void inject(ReportMapDetailController reportMapDetailController);
    void inject(ReportInputMapController reportInputMapController);

    void inject(ChooseCategoryController chooseCategoryController);

    void inject(MyCommentController myCommentController);
    void inject(ReportCommentController reportCommentController);
}
