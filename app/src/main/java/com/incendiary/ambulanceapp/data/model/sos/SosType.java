package com.incendiary.ambulanceapp.data.model.sos;

public enum SosType {

    POLISI("polisi"),
    DAMKAR("damkar");

    final String value;

    SosType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
