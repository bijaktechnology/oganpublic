package com.incendiary.ambulanceapp.utils.conductor;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;

public class Routes {

    public static RouterTransaction simpleTransaction(Controller controller, ControllerChangeHandler changeHandler) {
        ControllerChangeHandler popChangeHandler;
        try {
            popChangeHandler = changeHandler.getClass().newInstance();
        } catch (Exception e) {
            popChangeHandler = new VerticalChangeHandler();
        }
        return RouterTransaction.with(controller)
                .pushChangeHandler(changeHandler)
                .popChangeHandler(popChangeHandler);
    }

    public static boolean handleBackWithMinBackStack(Router router, int minBackStack) {
        return isHaveMoreBackStack(router, minBackStack) && router.handleBack();
    }

    private static boolean isHaveMoreBackStack(Router router, int minBackStack) {
        if (router.getBackstackSize() > minBackStack) {
            return true;
        }
        for (RouterTransaction routerTransaction : router.getBackstack()) {
            for (Router childRouter : routerTransaction.controller().getChildRouters()) {
                if (isHaveMoreBackStack(childRouter, minBackStack)) {
                    return true;
                }
            }
        }
        return false;
    }
}
