package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;

public class RekamMedis {

    @SerializedName("medical_record_id") private String medicalRecordId;
    @SerializedName("user_id") private String userId;
    @SerializedName("diagnosa") private String diagnosa;
    @SerializedName("rs_puskesmas") private String rsPuskesmas;
    @SerializedName("tanggal") private String tanggal;
    @SerializedName("tindakan") private String tindakan;
    @SerializedName("nama_dokter") private String namaDokter;

    /**
     * @return The medicalRecordId
     */
    public String getMedicalRecordId() {
        return medicalRecordId;
    }

    /**
     * @param medicalRecordId The medical_record_id
     */
    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The diagnosa
     */
    public String getDiagnosa() {
        return diagnosa;
    }

    /**
     * @param diagnosa The diagnosa
     */
    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    /**
     * @return The rsPuskesmas
     */
    public String getRsPuskesmas() {
        return rsPuskesmas;
    }

    /**
     * @param rsPuskesmas The rs_puskesmas
     */
    public void setRsPuskesmas(String rsPuskesmas) {
        this.rsPuskesmas = rsPuskesmas;
    }

    /**
     * @return The tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal The tanggal
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return The tindakan
     */
    public String getTindakan() {
        return tindakan;
    }

    /**
     * @param tindakan The tindakan
     */
    public void setTindakan(String tindakan) {
        this.tindakan = tindakan;
    }

    /**
     * @return The namaDokter
     */
    public String getNamaDokter() {
        return namaDokter;
    }

    /**
     * @param namaDokter The nama_dokter
     */
    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }
}
