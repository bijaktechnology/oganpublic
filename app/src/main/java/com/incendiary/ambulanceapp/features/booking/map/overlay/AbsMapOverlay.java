package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.androidcommon.android.views.Animates;

public abstract class AbsMapOverlay implements MapOverlay {

    private ViewGroup viewGroup;

    @LayoutRes
    abstract int getLayoutResId();

    abstract void bindView(View view, MapMarker mapMarker);

    @Override
    public void handleMarker(MapMarker mapMarker, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        viewGroup.removeAllViews();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutResId(), viewGroup, false);
        viewGroup.addView(view);
        bindView(view, mapMarker);
    }

    private ViewGroup getContainerView() {
        return viewGroup;
    }

    protected void hideOverlay() {
        Animates.visibility(viewGroup, false);
    }
}
