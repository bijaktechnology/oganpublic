package com.incendiary.ambulanceapp.features.settings.misc;

public class DisclaimerController extends WebViewController {

    private static final String DISCLAIMER_URL = "http://103.247.11.226/app/about/index.php?disclaimer";

    @Override
    protected String getUrl() {
        return DISCLAIMER_URL;
    }

    @Override
    protected String getTitle() {
        return "Disclaimer";
    }
}
