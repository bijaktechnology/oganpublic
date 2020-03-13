package com.incendiary.ambulanceapp.data.model.presentermodel;

public class RegisterParam {

    private final String username;
    private final String email;
    private final String name;
    private final String password;
    private final String noKtp;
    private final String noTelp;
    private final String motherName;
    private final String dob;
    private final int domicileStatus;

    public RegisterParam(String username, String email, String name, String password, String noKtp, String noTelp,
                         String motherName, String dob, int domicileStatus) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.noKtp = noKtp;
        this.noTelp = noTelp;
        this.motherName = motherName;
        this.dob = dob;
        this.domicileStatus = domicileStatus;
    }

    public String getUsername() {
        return username;
    }

    public int getDomicileStatus() {
        return domicileStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getDob() {
        return dob;
    }
}
