package com.incendiary.ambulanceapp.data.model.poi.atmberas;

import com.google.gson.annotations.SerializedName;

public class AtmBerasInfo {

    /**
     * unit_id : 62
     * unit_name : ATM Beras Bungursari
     * alamat : Jl. Veteran, Bungursari, Kabupaten Purwakarta, Jawa Barat 41181
     * deskripsi : ATM Beras di Kecamatan Bungursari
     * stock : 100
     * capacity : 100
     * pct_stock : 100
     */

    @SerializedName("unit_id") private String unitId;
    @SerializedName("unit_name") private String unitName;
    @SerializedName("alamat") private String alamat;
    @SerializedName("deskripsi") private String deskripsi;
    @SerializedName("stock") private String stock;
    @SerializedName("capacity") private String capacity;
    @SerializedName("pct_stock") private int pctStock;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getPctStock() {
        return pctStock;
    }

    public void setPctStock(int pctStock) {
        this.pctStock = pctStock;
    }
}
