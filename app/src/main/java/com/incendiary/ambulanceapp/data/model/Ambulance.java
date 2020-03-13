package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class Ambulance implements MapMarker {

    /**
     * unit_id : 1
     * no_plat : T 9976 A
     * nama_driver : -
     * lat : -6.55972
     * lng : 107.446
     * base_location : Puskesmas Purwakarta
     * status : A
     */

    @SerializedName("unit_id") private String unitId;
    @SerializedName("no_plat") private String noPlat;
    @SerializedName("nama_driver") private String namaDriver;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("base_location") private String baseLocation;
    @SerializedName("status") private String status;

  /* --------------------------------------------------- */
  /* > Marker */
  /* --------------------------------------------------- */

    @Override
    public int getMarkerType() {
        return MarkerType.AMBULANCE;
    }

    @Override
    public String getLat() {
        return lat;
    }

    @Override
    public String getLang() {
        return lng;
    }

    @Override
    public String getTitle() {
        return noPlat;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_ambulance;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
