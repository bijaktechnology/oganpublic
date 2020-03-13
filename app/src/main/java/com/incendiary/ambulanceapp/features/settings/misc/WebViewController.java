package com.incendiary.ambulanceapp.features.settings.misc;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public abstract class WebViewController extends AbsController {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.webview) WebView webView;
    @BindView(R.id.webview_progress) ProgressBar progressBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebClient(progressBar));
        webView.loadUrl(getUrl());
    }

    protected abstract String getUrl();
    protected abstract String getTitle();

    private static class WebClient extends android.webkit.WebViewClient {

        private final WeakReference<View> progress;

        WebClient(View progress) {
            this.progress = new WeakReference<>(progress);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (progress.get() != null) {
                progress.get().setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (progress.get() != null) {
                progress.get().setVisibility(View.GONE);
            }
        }
    }
}
