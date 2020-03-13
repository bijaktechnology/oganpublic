package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;

public class HistoryBooking {

    /**
     * user_id : 1
     * timestamp : 2017-05-16 23:48:07
     * type : ambulance
     */

    @SerializedName("user_id") private String userId;
    @SerializedName("timestamp") private String timestamp;
    @SerializedName("type") private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
