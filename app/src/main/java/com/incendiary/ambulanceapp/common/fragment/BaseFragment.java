package com.incendiary.ambulanceapp.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esafirm.emvipi.Presenter;
import com.esafirm.emvipi.view.MvpView;
import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.dagger.HasComponent;
import com.incendiary.ambulanceapp.utils.EBus;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

public abstract class BaseFragment extends RxFragment {

    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    protected abstract int getLayoutResId();

    protected boolean hasEvent() {
        return false;
    }

    protected void onInjectComponent() {
    }

    @SuppressWarnings("unchecked")
    protected void bindPresenter(Presenter presenter, MvpView view) {
        presenter.attachView(view);
        lifecycle()
                .filter(fragmentEvent -> fragmentEvent == FragmentEvent.DESTROY_VIEW)
                .forEach(fragmentEvent -> presenter.detachView());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (hasEvent())
            EBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EBus.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInjectComponent();
    }

  /* --------------------------------------------------- */
  /* > Convenience Methods */
  /* --------------------------------------------------- */

    @SuppressWarnings("unchecked")
    protected <T extends Fragment> T findFragment(@IdRes int id) {
        return (T) getChildFragmentManager().findFragmentById(id);
    }

    public void finish() {
        if (isAdded())
            getActivity().finish();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
