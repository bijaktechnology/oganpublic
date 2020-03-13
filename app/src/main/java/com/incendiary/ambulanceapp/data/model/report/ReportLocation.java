package com.incendiary.ambulanceapp.data.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReportLocation implements Parcelable {

    /**
     * country : Indonesia
     * province : Jawa Barat
     * city : null
     * street :  Jalan Panguban Batujajar
     * postal_code : 40561
     * country_code : ID
     * formatted_address : Jalan Panguban Batujajar, Pangauban, Batujajar, Kabupaten Bandung Barat, Jawa Barat 40561, Indonesia
     */

    @SerializedName("country") private String country;
    @SerializedName("province") private String province;
    @SerializedName("city") private String city;
    @SerializedName("street") private String street;
    @SerializedName("postal_code") private String postalCode;
    @SerializedName("country_code") private String countryCode;
    @SerializedName("formatted_address") private String formattedAddress;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public ReportLocation() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.street);
        dest.writeString(this.postalCode);
        dest.writeString(this.countryCode);
        dest.writeString(this.formattedAddress);
    }

    protected ReportLocation(Parcel in) {
        this.country = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.street = in.readString();
        this.postalCode = in.readString();
        this.countryCode = in.readString();
        this.formattedAddress = in.readString();
    }

    public static final Creator<ReportLocation> CREATOR = new Creator<ReportLocation>() {
        @Override
        public ReportLocation createFromParcel(Parcel source) {
            return new ReportLocation(source);
        }

        @Override
        public ReportLocation[] newArray(int size) {
            return new ReportLocation[size];
        }
    };
}
