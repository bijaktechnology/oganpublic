package com.incendiary.ambulanceapp.data.model.tours;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TourPlace implements Parcelable {

    /**
     * id : 1
     * kategori : wisata
     * nama_destinasi_wisata : Taman Sri Baduga
     * alamat : Negeri Kidul, Purwakarta
     * desa_kelurahan : Nagri Kidul
     * kecamatan : Purwakarta
     * kab_kota : Purwakarta
     * provinsi : Jawa Barat
     * image : 103.247.11.226/app/image/content_wisata_1.jpg
     * lat : -6.55707979202271
     * lng : 107.445999145508
     * distance : 1.5
     * deskripsi : lorem ipsum
     */

    @SerializedName("id") private String id;
    @SerializedName("kategori") private String kategori;
    @SerializedName("nama_destinasi_wisata") private String namaDestinasiWisata;
    @SerializedName("alamat") private String alamat;
    @SerializedName("desa_kelurahan") private String desaKelurahan;
    @SerializedName("kecamatan") private String kecamatan;
    @SerializedName("kab_kota") private String kabKota;
    @SerializedName("provinsi") private String provinsi;
    @SerializedName("image") private String image;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("distance") private String distance;
    @SerializedName("deskripsi") private String deskripsi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNamaDestinasiWisata() {
        return namaDestinasiWisata;
    }

    public void setNamaDestinasiWisata(String namaDestinasiWisata) {
        this.namaDestinasiWisata = namaDestinasiWisata;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDesaKelurahan() {
        return desaKelurahan;
    }

    public void setDesaKelurahan(String desaKelurahan) {
        this.desaKelurahan = desaKelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabKota() {
        return kabKota;
    }

    public void setKabKota(String kabKota) {
        this.kabKota = kabKota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.kategori);
        dest.writeString(this.namaDestinasiWisata);
        dest.writeString(this.alamat);
        dest.writeString(this.desaKelurahan);
        dest.writeString(this.kecamatan);
        dest.writeString(this.kabKota);
        dest.writeString(this.provinsi);
        dest.writeString(this.image);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.distance);
        dest.writeString(this.deskripsi);
    }

    public TourPlace() {
    }

    protected TourPlace(Parcel in) {
        this.id = in.readString();
        this.kategori = in.readString();
        this.namaDestinasiWisata = in.readString();
        this.alamat = in.readString();
        this.desaKelurahan = in.readString();
        this.kecamatan = in.readString();
        this.kabKota = in.readString();
        this.provinsi = in.readString();
        this.image = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.distance = in.readString();
        this.deskripsi = in.readString();
    }

    public static final Parcelable.Creator<TourPlace> CREATOR = new Parcelable.Creator<TourPlace>() {
        @Override
        public TourPlace createFromParcel(Parcel source) {
            return new TourPlace(source);
        }

        @Override
        public TourPlace[] newArray(int size) {
            return new TourPlace[size];
        }
    };
}
