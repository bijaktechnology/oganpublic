package com.incendiary.ambulanceapp.utils;

import android.support.v4.widget.SwipeRefreshLayout;

public class SwipeRefreshUtils {
    public static void showLoading(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading) {
        if (swipeRefreshLayout != null) {
            Runnable action = new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(isLoading);
                        swipeRefreshLayout.removeCallbacks(this);
                    }
                }
            };
            swipeRefreshLayout.post(action);
        }
    }
}
