package com.incendiary.ambulanceapp.data.events;

import com.google.android.gms.common.api.Status;

public class EventLocationResolveRequest {

    private final Status status;

    public EventLocationResolveRequest(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
