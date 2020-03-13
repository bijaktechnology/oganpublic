package com.incendiary.ambulanceapp.features.booking;

import android.view.View;

public enum BookingViewState {
    NORMAL,
    MARKER_SELECTED;

    /**
     * if current state equal to @paramter state, views will be visible otherwise gone
     **/
    public void visibleIf(BookingViewState state, View... view) {
        for (View v : view) {
            v.setVisibility(state == this ? View.VISIBLE : View.GONE);
        }
    }
}
