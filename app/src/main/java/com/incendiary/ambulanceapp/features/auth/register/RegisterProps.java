package com.incendiary.ambulanceapp.features.auth.register;

import android.os.Parcel;
import android.os.Parcelable;

public class RegisterProps implements Parcelable {

    private final DomicileStatus domicileStatus;
    private final String nik;

    public RegisterProps(DomicileStatus domicileStatus, String nik) {
        this.domicileStatus = domicileStatus;
        this.nik = nik;
    }

    public DomicileStatus getDomicileStatus() {
        return domicileStatus;
    }

    public String getNik() {
        return nik;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.domicileStatus == null ? -1 : this.domicileStatus.ordinal());
        dest.writeString(this.nik);
    }

    protected RegisterProps(Parcel in) {
        int tmpDomicileStatus = in.readInt();
        this.domicileStatus = tmpDomicileStatus == -1 ? null : DomicileStatus.values()[tmpDomicileStatus];
        this.nik = in.readString();
    }

    public static final Parcelable.Creator<RegisterProps> CREATOR = new Parcelable.Creator<RegisterProps>() {
        @Override
        public RegisterProps createFromParcel(Parcel source) {
            return new RegisterProps(source);
        }

        @Override
        public RegisterProps[] newArray(int size) {
            return new RegisterProps[size];
        }
    };
}
