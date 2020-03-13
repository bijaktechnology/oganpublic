package com.incendiary.ambulanceapp.features.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.incendiary.androidcommon.etc.Logger;

public class MyInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Logger.log(Log.DEBUG, "Token:" + token);
    }
}
