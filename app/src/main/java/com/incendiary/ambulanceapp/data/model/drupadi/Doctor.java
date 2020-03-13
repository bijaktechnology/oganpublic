package com.incendiary.ambulanceapp.data.model.drupadi;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class Doctor extends MedPerson implements MapMarker {

    /**
     * dokter_id : 1
     * nama_dokter : dr. Anton
     * alamat : PURWAKARTA JAWA BARAT
     * lat : -6.55972
     * lng : 107.446
     * base_location : Puskesmas Purwakarta
     * status : A
     */

    @SerializedName("dokter_id") private String dokterId;
    @SerializedName("nama_dokter") private String namaDokter;
    @SerializedName("alamat") private String alamat;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("base_location") private String baseLocation;
    @SerializedName("status") private String status;

    @Override
    public int getMarkerType() {
        return MarkerType.DOCTOR;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_doctor;
    }

    public String getDokterId() {
        return dokterId;
    }

    public void setDokterId(String dokterId) {
        this.dokterId = dokterId;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    @Override
    public String getLang() {
        return getLng();
    }

    @Override
    public String getTitle() {
        return getNamaDokter();
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

    public String getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
