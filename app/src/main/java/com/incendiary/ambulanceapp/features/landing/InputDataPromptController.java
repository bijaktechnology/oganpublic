package com.incendiary.ambulanceapp.features.landing;

import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.features.authconfirm.DomicileConfirmationController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.utils.Preconditions;

import butterknife.OnClick;

public class InputDataPromptController extends AbsController {

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_prompt_input_data;
    }

    @Override
    protected void onViewBound(View view) {
    }

    @OnClick({
            R.id.prompt_input_data_btn_exit,
            R.id.prompt_input_data_container
    })
    void onExitClick() {
        popCurrentController();
    }

    @OnClick(R.id.prompt_input_data_btn_input)
    void onInputClick() {
        Controller controller = getParentController();
        Preconditions.checkNotNull(controller);

        controller.getRouter().pushController(Routes.simpleTransaction(
                new DomicileConfirmationController(true),
                new FadeChangeHandler()
        ));

        popCurrentController();
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public static void show(Router childRouter) {
        childRouter.setPopsLastView(true)
                .setRoot(Routes.simpleTransaction(
                        new InputDataPromptController(),
                        new FadeChangeHandler()
                ));
    }
}
