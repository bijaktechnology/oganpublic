package com.incendiary.ambulanceapp.features.errors;

import com.incendiary.ambulanceapp.R;
import com.incendiary.androidcommon.android.ContextProvider;

public class ErrorFactory {

    public static Throwable createNetworkError(){
        return new IllegalStateException(ContextProvider.get().getString(R.string.error_connection));
    }

}
