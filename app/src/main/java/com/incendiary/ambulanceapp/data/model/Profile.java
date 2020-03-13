package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.features.auth.register.DomicileStatus;

public class Profile {

    /**
     * user_id : 1
     * username : usertest
     * nik : 1234567890123456
     * nama_lengkap : User Test
     * email : usertest@usertest.com
     * no_hp : 08123456789
     * alamat : null
     * status_domisili : 1
     * user_img : http://103.247.11.226/app/image/avatar_1_0.jpg
     * keycode : 92d4b1b1b3d6d16d58856c0431c15b78
     */

    @SerializedName("user_id") private String userId;
    @SerializedName("username") private String username;
    @SerializedName("nik") private String nik;
    @SerializedName("nama_lengkap") private String namaLengkap;
    @SerializedName("email") private String email;
    @SerializedName("no_hp") private String noHp;
    @SerializedName("alamat") private Object alamat;
    @SerializedName("status_domisili") private DomicileStatus statusDomisili;
    @SerializedName("user_img") private String userImg;
    @SerializedName("keycode") private String keycode;
    @SerializedName("tgl_lahir") private String tglLahir;
    @SerializedName("nama_ibu_kandung") private String namaIbuKandung;
    @SerializedName("push_notif") private String pushNotif;

    public boolean isPushNotifEnabled() {
        return pushNotif != null && pushNotif.equalsIgnoreCase("1");
    }

    public void setPushNotif(String pushNotif) {
        this.pushNotif = pushNotif;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Object getAlamat() {
        return alamat;
    }

    public void setAlamat(Object alamat) {
        this.alamat = alamat;
    }

    public DomicileStatus getStatusDomisili() {
        return statusDomisili;
    }

    public void setStatusDomisili(DomicileStatus statusDomisili) {
        this.statusDomisili = statusDomisili;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
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
}
