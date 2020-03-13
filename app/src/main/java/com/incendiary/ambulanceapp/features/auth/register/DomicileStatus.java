package com.incendiary.ambulanceapp.features.auth.register;

import com.google.gson.annotations.SerializedName;

public enum DomicileStatus {
    @SerializedName(value = "1")PURWAKARTA(1),
    @SerializedName(value = "2")NON_PURWAKARTA(2);

    private final int value;

    DomicileStatus(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
