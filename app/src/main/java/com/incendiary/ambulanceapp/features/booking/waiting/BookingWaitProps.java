package com.incendiary.ambulanceapp.features.booking.waiting;

import android.os.Parcel;
import android.os.Parcelable;

public class BookingWaitProps implements Parcelable {

    private final int type;
    private final String bookingId;

    public BookingWaitProps(int type, String bookingId) {
        this.type = type;
        this.bookingId = bookingId;
    }

    public int getType() {
        return type;
    }

    public String getBookingId() {
        return bookingId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.bookingId);
    }

    protected BookingWaitProps(Parcel in) {
        this.type = in.readInt();
        this.bookingId = in.readString();
    }

    public static final Parcelable.Creator<BookingWaitProps> CREATOR = new Parcelable.Creator<BookingWaitProps>() {
        @Override
        public BookingWaitProps createFromParcel(Parcel source) {
            return new BookingWaitProps(source);
        }

        @Override
        public BookingWaitProps[] newArray(int size) {
            return new BookingWaitProps[size];
        }
    };
}
