package com.incendiary.ambulanceapp.data.model.fcm;

import android.os.Parcel;
import android.os.Parcelable;

import com.incendiary.ambulanceapp.utils.Numbers;

import java.util.Map;

public class FcmNotification implements Parcelable {

    private final String notificationId;
    private final String extraId;
    private final String notificationTitle;
    private final String notificationMessage;
    private final int notificationType;


    public FcmNotification(String notificationId, String extraId, String notificationTitle, String notificationMessage,
                           int notificationType) {
        this.notificationId = notificationId;
        this.extraId = extraId;
        this.notificationTitle = notificationTitle;
        this.notificationMessage = notificationMessage;
        this.notificationType = notificationType;
    }

    public Integer getSafeNotificationId() {
        try {
            return Integer.valueOf(getNotificationId());
        } catch (Exception e) {
            return 1;
        }
    }

    public int getNotificationType() {
        return notificationType;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getExtraId() {
        return extraId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public static FcmNotification fromRemoteMessageData(Map<String, String> data) {
        return new FcmNotification(
                data.get("notification_id"),
                data.get("laporan_id"),
                data.get("notification_header"),
                data.get("notification_body"),
                Numbers.safeInteger(data.get("notification_type_id"), 0)
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notificationId);
        dest.writeString(this.extraId);
        dest.writeString(this.notificationTitle);
        dest.writeString(this.notificationMessage);
        dest.writeInt(this.notificationType);
    }

    protected FcmNotification(Parcel in) {
        this.notificationId = in.readString();
        this.extraId = in.readString();
        this.notificationTitle = in.readString();
        this.notificationMessage = in.readString();
        this.notificationType = in.readInt();
    }

    public static final Creator<FcmNotification> CREATOR = new Creator<FcmNotification>() {
        @Override
        public FcmNotification createFromParcel(Parcel source) {
            return new FcmNotification(source);
        }

        @Override
        public FcmNotification[] newArray(int size) {
            return new FcmNotification[size];
        }
    };
}
