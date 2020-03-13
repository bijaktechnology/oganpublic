package com.incendiary.ambulanceapp.features.errors;

import com.esafirm.emvipi.view.MvpErrorView;
import com.incendiary.androidcommon.etc.Logger;

public class ErrorHandler {

    public static void handleError(Throwable throwable) {
        handleError(throwable, null);
    }

    public static void handleError(Throwable throwable, MvpErrorView errorView) {
        Logger.log(throwable);

        if (errorView != null) {
            if (throwable == null) {
                throwable = ErrorFactory.createNetworkError();
            }
            errorView.showError(throwable);
        }
    }

}
