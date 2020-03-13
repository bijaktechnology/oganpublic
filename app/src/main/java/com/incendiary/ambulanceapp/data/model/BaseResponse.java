package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("success") private boolean success = true;
    @SerializedName("message") private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
