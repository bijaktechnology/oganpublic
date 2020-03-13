package com.incendiary.ambulanceapp.data.model.report;

public class ReportParameter {

    private final String lat;
    private final String lng;
    private final String photo;
    private final String userId;

    public ReportParameter(String lat, String lng, String photo, String userId) {
        this.lat = lat;
        this.lng = lng;
        this.photo = photo;
        this.userId = userId;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUserId() {
        return userId;
    }
}
