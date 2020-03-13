package com.incendiary.ambulanceapp.data.model.notification;

import com.google.gson.annotations.SerializedName;

public class SemarNotification {

    /**
     * notification_id : 1
     * laporan_id : 1
     * notification_header : Laporan Diproses
     * notification_body : Laporan anda sedang ditangni oleh petugas
     * notification_time : 2017-05-14 12:04:20
     * is_read : 0
     */

    @SerializedName("notification_id") private String notificationId;
    @SerializedName("laporan_id") private String laporanId;
    @SerializedName("notification_header") private String notificationHeader;
    @SerializedName("notification_body") private String notificationBody;
    @SerializedName("notification_time") private String notificationTime;
    @SerializedName("notification_type_id") private int notificationType;
    @SerializedName("is_read") private String isRead;

    public int getNotificationType() {
        return notificationType;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getLaporanId() {
        return laporanId;
    }

    public void setLaporanId(String laporanId) {
        this.laporanId = laporanId;
    }

    public String getNotificationHeader() {
        return notificationHeader;
    }

    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
