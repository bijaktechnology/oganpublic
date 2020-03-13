package com.incendiary.ambulanceapp.data.model.common;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.data.model.BaseResponse;

public class ApiResponse<T> extends BaseResponse {

    @SerializedName("data") T data;

    public T getData() {
        return data;
    }
}
