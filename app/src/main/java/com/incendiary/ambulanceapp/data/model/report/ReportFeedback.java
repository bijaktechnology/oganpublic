package com.incendiary.ambulanceapp.data.model.report;

import com.google.gson.annotations.SerializedName;

public class ReportFeedback {

    /**
     * laporan_id : 1
     * status_laporan : W
     * report_time : 2017-05-10 13:00:00
     * process_time : 2017-05-10 13:20:00
     * foto_laporan : 103.247.11.226/app/image/laporan_warga_1_1.jpg
     * petugas_id : 1
     * nama_petugas : petugas1
     * dinas_id : 5
     * nama_dinas : Dinas Lingkungan Hidup
     * complete_time :
     * foto_penyelesaian :
     */

    @SerializedName("laporan_id") private String laporanId;
    @SerializedName("status_laporan") private ReportStatus statusLaporan;
    @SerializedName("report_time") private String reportTime;
    @SerializedName("process_time") private String processTime;
    @SerializedName("foto_laporan") private String fotoLaporan;
    @SerializedName("petugas_id") private String petugasId;
    @SerializedName("nama_petugas") private String namaPetugas;
    @SerializedName("dinas_id") private String dinasId;
    @SerializedName("nama_dinas") private String namaDinas;
    @SerializedName("complete_time") private String completeTime;
    @SerializedName("foto_penyelesaian") private String fotoPenyelesaian;

    public String getLaporanId() {
        return laporanId;
    }

    public void setLaporanId(String laporanId) {
        this.laporanId = laporanId;
    }

    public ReportStatus getStatusLaporan() {
        return statusLaporan;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public String getFotoLaporan() {
        return fotoLaporan;
    }

    public void setFotoLaporan(String fotoLaporan) {
        this.fotoLaporan = fotoLaporan;
    }

    public String getPetugasId() {
        return petugasId;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getDinasId() {
        return dinasId;
    }

    public void setDinasId(String dinasId) {
        this.dinasId = dinasId;
    }

    public String getNamaDinas() {
        return namaDinas;
    }

    public void setNamaDinas(String namaDinas) {
        this.namaDinas = namaDinas;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getFotoPenyelesaian() {
        return fotoPenyelesaian;
    }

    public void setFotoPenyelesaian(String fotoPenyelesaian) {
        this.fotoPenyelesaian = fotoPenyelesaian;
    }
}
