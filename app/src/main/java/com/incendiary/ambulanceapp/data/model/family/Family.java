package com.incendiary.ambulanceapp.data.model.family;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Family implements Parcelable {

    /**
     * anggota_id : 2
     * nik : 1234567890123456
     * nama_lengkap : def
     * tgl_lahir : 1992-01-01
     * nama_ibu_kandung : sulis
     * status_keluarga : anak
     */

    @SerializedName("anggota_id") private String anggotaId;
    @SerializedName("nik") private String nik;
    @SerializedName("nama_lengkap") private String namaLengkap;
    @SerializedName("tgl_lahir") private String tglLahir;
    @SerializedName("nama_ibu_kandung") private String namaIbuKandung;
    @SerializedName("status_keluarga") private String statusKeluarga;

    public Family(String anggotaId, String nik, String namaLengkap, String tglLahir, String namaIbuKandung, String statusKeluarga) {
        this.anggotaId = anggotaId;
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.tglLahir = tglLahir;
        this.namaIbuKandung = namaIbuKandung;
        this.statusKeluarga = statusKeluarga;
    }

    public Family(String nik, String namaLengkap, String tglLahir, String namaIbuKandung, String statusKeluarga) {
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.tglLahir = tglLahir;
        this.namaIbuKandung = namaIbuKandung;
        this.statusKeluarga = statusKeluarga;
    }

    public String getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(String anggotaId) {
        this.anggotaId = anggotaId;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getNamaIbuKandung() {
        return namaIbuKandung;
    }

    public void setNamaIbuKandung(String namaIbuKandung) {
        this.namaIbuKandung = namaIbuKandung;
    }

    public String getStatusKeluarga() {
        return statusKeluarga;
    }

    public void setStatusKeluarga(String statusKeluarga) {
        this.statusKeluarga = statusKeluarga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.anggotaId);
        dest.writeString(this.nik);
        dest.writeString(this.namaLengkap);
        dest.writeString(this.tglLahir);
        dest.writeString(this.namaIbuKandung);
        dest.writeString(this.statusKeluarga);
    }

    public Family() {
    }

    protected Family(Parcel in) {
        this.anggotaId = in.readString();
        this.nik = in.readString();
        this.namaLengkap = in.readString();
        this.tglLahir = in.readString();
        this.namaIbuKandung = in.readString();
        this.statusKeluarga = in.readString();
    }

    public static final Parcelable.Creator<Family> CREATOR = new Parcelable.Creator<Family>() {
        @Override
        public Family createFromParcel(Parcel source) {
            return new Family(source);
        }

        @Override
        public Family[] newArray(int size) {
            return new Family[size];
        }
    };
}
