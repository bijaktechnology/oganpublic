package com.incendiary.ambulanceapp.features.booking.patientdata;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientDataProps implements Parcelable {

    private final String bookingId;
    private final int markerType;

    public PatientDataProps(String bookingId, int markerType) {
        this.bookingId = bookingId;
        this.markerType = markerType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getMarkerType() {
        return markerType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookingId);
        dest.writeInt(this.markerType);
    }

    protected PatientDataProps(Parcel in) {
        this.bookingId = in.readString();
        this.markerType = in.readInt();
    }

    public static final Parcelable.Creator<PatientDataProps> CREATOR = new Parcelable.Creator<PatientDataProps>() {
        @Override
        public PatientDataProps createFromParcel(Parcel source) {
            return new PatientDataProps(source);
        }

        @Override
        public PatientDataProps[] newArray(int size) {
            return new PatientDataProps[size];
        }
    };
}
