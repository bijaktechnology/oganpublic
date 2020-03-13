package com.incendiary.ambulanceapp.data.model.exceptions;

public class NoLocationException extends Exception {

    private static final String DEFAULT_MESSAGE = "Tidak dapat mendapatkan lokasi";

    @Override
    public String getMessage() {
        return DEFAULT_MESSAGE;
    }
}
