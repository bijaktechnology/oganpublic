package com.incendiary.ambulanceapp.data.model.report;

import com.google.gson.annotations.SerializedName;

public enum ReportStatus {
    @SerializedName("W")WAITING,
    @SerializedName("P")PROCESSING,
    @SerializedName("C")COMPLETE
}
