package com.incendiary.ambulanceapp.data.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Report implements Parcelable {

    @SerializedName("laporan_id") private String laporanId;
    @SerializedName("user_id") private String userId;
    @SerializedName("nama_lengkap") private String namaLengkap;
    @SerializedName("kategori_id") private String kategoriId;
    @SerializedName("nama_kategori") private String namaKategori;
    @SerializedName("judul") private String judul;
    @SerializedName("keterangan") private String keterangan;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("foto_laporan") private String fotoLaporan;
    @SerializedName("report_time") private String reportTime;
    @SerializedName("status_laporan") private ReportStatus statusLaporan;
    @SerializedName("jumlah_like") private String jumlahLike;
    @SerializedName("jumlah_comment") private String jumlahComment;
    @SerializedName("icon") private String icon;
    @SerializedName("location") private ReportLocation reportLocation;
    @SerializedName("user_img") private String avatar;
    @SerializedName("status_bookmark") private boolean statusBookmark;
    @SerializedName("status_like") private boolean statusLike;

    public Report(String laporanId) {
        this.laporanId = laporanId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getIcon() {
        return icon;
    }

    public ReportLocation getReportLocation() {
        return reportLocation;
    }

    public String getLaporanId() {
        return laporanId;
    }

    public String getUserId() {
        return userId;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getKategoriId() {
        return kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public String getJudul() {
        return judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getFotoLaporan() {
        return fotoLaporan;
    }

    public String getReportTime() {
        return reportTime;
    }

    public ReportStatus getStatusLaporan() {
        return statusLaporan;
    }

    public String getJumlahLike() {
        return jumlahLike;
    }

    public String getJumlahComment() {
        return jumlahComment;
    }

    public boolean isStatusBookmark() {
        return statusBookmark;
    }

    public void setStatusBookmark(boolean statusBookmark) {
        this.statusBookmark = statusBookmark;
    }

    public boolean isStatusLike() {
        return statusLike;
    }

    public void setStatusLike(boolean statusLike) {
        this.statusLike = statusLike;
    }

    public void setJumlahLike(String jumlahLike) {
        this.jumlahLike = jumlahLike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.laporanId);
        dest.writeString(this.userId);
        dest.writeString(this.namaLengkap);
        dest.writeString(this.kategoriId);
        dest.writeString(this.namaKategori);
        dest.writeString(this.judul);
        dest.writeString(this.keterangan);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.fotoLaporan);
        dest.writeString(this.reportTime);
        dest.writeInt(this.statusLaporan == null ? -1 : this.statusLaporan.ordinal());
        dest.writeString(this.jumlahLike);
        dest.writeString(this.jumlahComment);
        dest.writeString(this.icon);
        dest.writeParcelable(this.reportLocation, flags);
        dest.writeString(this.avatar);
        dest.writeByte(this.statusBookmark ? (byte) 1 : (byte) 0);
        dest.writeByte(this.statusLike ? (byte) 1 : (byte) 0);
    }

    protected Report(Parcel in) {
        this.laporanId = in.readString();
        this.userId = in.readString();
        this.namaLengkap = in.readString();
        this.kategoriId = in.readString();
        this.namaKategori = in.readString();
        this.judul = in.readString();
        this.keterangan = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.fotoLaporan = in.readString();
        this.reportTime = in.readString();
        int tmpStatusLaporan = in.readInt();
        this.statusLaporan = tmpStatusLaporan == -1 ? null : ReportStatus.values()[tmpStatusLaporan];
        this.jumlahLike = in.readString();
        this.jumlahComment = in.readString();
        this.icon = in.readString();
        this.reportLocation = in.readParcelable(ReportLocation.class.getClassLoader());
        this.avatar = in.readString();
        this.statusBookmark = in.readByte() != 0;
        this.statusLike = in.readByte() != 0;
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel source) {
            return new Report(source);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
