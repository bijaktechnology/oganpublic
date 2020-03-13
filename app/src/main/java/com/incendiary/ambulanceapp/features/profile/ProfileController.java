package com.incendiary.ambulanceapp.features.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.data.model.presentermodel.EditProfileParam;
import com.incendiary.ambulanceapp.features.authconfirm.DomicileConfirmationController;
import com.incendiary.ambulanceapp.features.profile.familylist.FamilyListController;
import com.incendiary.ambulanceapp.features.profile.presenter.ProfilePresenter;
import com.incendiary.ambulanceapp.features.profile.view.ProfileView;
import com.incendiary.ambulanceapp.utils.Bitmaps;
import com.incendiary.ambulanceapp.utils.conductor.ControllerStartable;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.rx.RxEditText;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Intents;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileController extends AbsController implements ProfileView {

    private static final int RC_IMAGE_REQUEST = 0x123;

    @BindView(R.id.profile_img_avatar) ImageView imgAvatar;
    @BindView(R.id.profile_inp_name) EditText inpName;
    @BindView(R.id.profile_inp_email) EditText inpEmail;
    @BindView(R.id.profile_inp_no_ktp) EditText inpNoKtp;
    @BindView(R.id.profile_inp_phone) EditText inpPhone;
    @BindView(R.id.profile_inp_password) EditText inpPassword;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.profile_inp_username) TextInputEditText inpUsername;
    @BindView(R.id.profile_inp_dob) TextInputEditText inpDob;
    @BindView(R.id.profile_inp_mother_name) TextInputEditText inpMotherName;

    @Inject ProfilePresenter presenter;
    @Inject ProgressManager progressManager;

    private String imageString;

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_profile;
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        setupToolbar();
        presenter.loadProfile();

        RxEditText.bindDob(getActivity(), inpDob)
                .compose(bindToLifecycle())
                .forEach(s -> inpDob.setText(s));
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.profile);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_save:
                    onSaveClick();
                    break;
            }
            return true;
        });
    }

    private boolean isValid() {
        boolean isNotEmpty =
                ValidateUtils.runningValidationWithViews(ContextProvider.getString(R.string.error_empty), inpEmail,
                        inpName, inpNoKtp);

        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(inpEmail.getText().toString()).matches();
        if (!isEmailValid) {
            inpEmail.setError("Email must be well-formed");
        }
        return isEmailValid && isNotEmpty;
    }

  /* --------------------------------------------------- */
  /* > OnClick */
  /* --------------------------------------------------- */

    private void onSaveClick() {
        if (isValid()) {
            presenter.editProfile(
                    EditProfileParam.newBuilder()
                            .withUsername(inpUsername.getText().toString())
                            .withEmail(inpEmail.getText().toString())
                            .withName(inpName.getText().toString())
                            .withPassword(inpPassword.getText().toString())
                            .withNik(inpNoKtp.getText().toString())
                            .withNoTelp(inpPhone.getText().toString())
                            .withMotehrName(inpMotherName.getText().toString())
                            .withDob(inpDob.getText().toString())
                            .withUserImg(imageString)
                            .build());
        }
    }

    @OnClick(R.id.profile_btn_change_domicile)
    void onChangeDomicileClick() {
        getRouter().pushController(Routes.simpleTransaction(
                new DomicileConfirmationController(true),
                new HorizontalChangeHandler()
        ));
    }

    @OnClick(R.id.profile_btn_family_list)
    void onFamilyListClick() {
        getRouter().pushController(Routes.simpleTransaction(
                new FamilyListController(),
                new HorizontalChangeHandler()
        ));
    }

    @OnClick(R.id.profile_img_avatar)
    public void onImageChangeClick() {
        Intents.showImageChooser(new ControllerStartable(this), RC_IMAGE_REQUEST);
    }

  /* --------------------------------------------------- */
  /* > View Methods */
  /* --------------------------------------------------- */

    @Override
    public void showEditSuccess() {
        Toasts.show("Edit Success!");
    }

    @Override
    public void showLoading(boolean isLoading, int type) {
        progressManager.show(isLoading);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showProfile(Profile profile) {
        inpUsername.setText(profile.getUsername());
        inpName.setText(profile.getNamaLengkap());
        inpEmail.setText(profile.getEmail());
        inpNoKtp.setText(profile.getNik());
        inpPhone.setText(profile.getNoHp());
        inpMotherName.setText(profile.getNamaIbuKandung());
        inpDob.setText(profile.getTglLahir());

        Glide.with(ContextProvider.get())
                .load(profile.getUserImg())
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(imgAvatar);
    }

  /* --------------------------------------------------- */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Glide.with(ContextProvider.get())
                    .load(data.getData())
                    .asBitmap()
                    .transform(new CropCircleTransformation(getActivity()))
                    .into(new BitmapImageViewTarget(imgAvatar) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);
                            imageString = Bitmaps.bitmapToBase64String(
                                    Bitmaps.getScaledBitmap(resource, Bitmap.CompressFormat.JPEG, 80), 80);
                        }
                    });
        }
    }
}
