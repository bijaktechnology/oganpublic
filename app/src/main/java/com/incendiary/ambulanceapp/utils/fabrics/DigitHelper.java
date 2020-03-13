package com.incendiary.ambulanceapp.utils.fabrics;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.incendiary.androidcommon.utils.Preconditions;

import rx.functions.Action0;
import rx.functions.Action1;

public class DigitHelper {

    public static void authenticate(String phone, Action1<String> onSuccess, Action0 onError) {
        Preconditions.checkNotNull(phone);

        phone = phone.trim();
        if (!phone.startsWith("+62")) {
            phone = "+62" + phone;
        }

        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                .withAuthCallBack(new AuthCallback() {
                    @Override
                    public void success(DigitsSession session, String phoneNumber) {
                        onSuccess.call(phoneNumber);
                    }

                    @Override
                    public void failure(DigitsException error) {
                        onError.call();
                    }
                })
                .withPhoneNumber(phone);

        Digits.authenticate(authConfigBuilder.build());

    }
}
