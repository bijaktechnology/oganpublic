package com.incendiary.ambulanceapp.common;

import android.content.Intent;
import android.content.IntentSender;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.etc.Logger;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public abstract class BaseLocationAct extends BaseActivity {

    private static final int RC_LOCATION = 0x123;

    private ReactiveLocationProvider mReactiveLocationProvider =
            OganApp.component().locationProvider();

    protected void startGetLocation() {
        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100);

        mReactiveLocationProvider.checkLocationSettings(
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest)
                        .setAlwaysShow(true)
                        .build()
        ).subscribe(locationSettingsResult -> {
            Status status = locationSettingsResult.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    status.startResolutionForResult(this, RC_LOCATION);
                } catch (IntentSender.SendIntentException e) {
                    Logger.log(e);
                }
            } else {
                onLocationReady();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_LOCATION) {
            if (resultCode == RESULT_OK)
                onLocationReady();
            else {
                finish();
                Toasts.show("Please enable your location to continue");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void onLocationReady();
}
