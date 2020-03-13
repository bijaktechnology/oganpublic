package com.incendiary.ambulanceapp.data.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReportCategory implements Parcelable {

    /**
     * kategori_id : 1
     * nama_kategori : Sampah
     */

    @SerializedName("kategori_id") private String kategoriId;
    @SerializedName("nama_kategori") private String namaKategori;
    @SerializedName("icon") private String icon;

    public String getIcon() {
        return icon;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public ReportCategory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kategoriId);
        dest.writeString(this.namaKategori);
        dest.writeString(this.icon);
    }

    protected ReportCategory(Parcel in) {
        this.kategoriId = in.readString();
        this.namaKategori = in.readString();
        this.icon = in.readString();
    }

    public static final Creator<ReportCategory> CREATOR = new Creator<ReportCategory>() {
        @Override
        public ReportCategory createFromParcel(Parcel source) {
            return new ReportCategory(source);
        }

        @Override
        public ReportCategory[] newArray(int size) {
            return new ReportCategory[size];
        }
    };
}
