package com.incendiary.ambulanceapp.common.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.incendiary.ambulanceapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public abstract class ControllerActivity extends BaseActivity {

    @BindView(R.id.container) ViewGroup container;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_container_only);
        ButterKnife.bind(this);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        getRouterHandler().call(router);
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    protected abstract Controller getController();

    protected Action1<Router> getRouterHandler() {
        return r -> {
            if (!r.hasRootController()) {
                r.setRoot(RouterTransaction.with(getController()));
            }
        };
    }
}
