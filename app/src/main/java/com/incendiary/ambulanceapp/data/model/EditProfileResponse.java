package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;

public class EditProfileResponse extends BaseResponse{

    @SerializedName("row_affected") private int rowAffected;
    @SerializedName("new_value") private Profile profile;

    public int getRowAffected() {
        return rowAffected;
    }

    public void setRowAffected(int rowAffected) {
        this.rowAffected = rowAffected;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
