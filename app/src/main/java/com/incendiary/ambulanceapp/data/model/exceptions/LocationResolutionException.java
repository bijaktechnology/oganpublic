package com.incendiary.ambulanceapp.data.model.exceptions;

import com.google.android.gms.common.api.Status;

public class LocationResolutionException extends Throwable{

    private final Status status;

    public LocationResolutionException(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
