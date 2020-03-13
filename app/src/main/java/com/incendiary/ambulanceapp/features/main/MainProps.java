package com.incendiary.ambulanceapp.features.main;

import android.os.Parcel;
import android.os.Parcelable;

public class MainProps implements Parcelable {

    private final int mapType;
    private final boolean isWaiting;
    private final String bookingId;

    public MainProps(int mapType, boolean isWaiting, String bookingId) {
        this.mapType = mapType;
        this.isWaiting = isWaiting;
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getMapType() {
        return mapType;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mapType);
        dest.writeByte(this.isWaiting ? (byte) 1 : (byte) 0);
        dest.writeString(this.bookingId);
    }

    protected MainProps(Parcel in) {
        this.mapType = in.readInt();
        this.isWaiting = in.readByte() != 0;
        this.bookingId = in.readString();
    }

    public static final Parcelable.Creator<MainProps> CREATOR = new Parcelable.Creator<MainProps>() {
        @Override
        public MainProps createFromParcel(Parcel source) {
            return new MainProps(source);
        }

        @Override
        public MainProps[] newArray(int size) {
            return new MainProps[size];
        }
    };
}
