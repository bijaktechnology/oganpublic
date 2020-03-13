package com.incendiary.ambulanceapp.features.settings.misc;

public class AboutController extends WebViewController {

    private static final String ABOUT_URL = "http://103.247.11.226/app/about/index.php?about";

    @Override
    protected String getUrl() {
        return ABOUT_URL;
    }

    @Override
    protected String getTitle() {
        return "About";
    }
}
