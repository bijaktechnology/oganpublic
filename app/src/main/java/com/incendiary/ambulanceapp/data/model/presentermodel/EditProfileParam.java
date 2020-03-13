package com.incendiary.ambulanceapp.data.model.presentermodel;

public class EditProfileParam {

    private final String username;
    private final String email;
    private final String password;
    private final String name;
    private final String noTelp;
    private final String nik;
    private final String userImg;
    private final String dob;
    private final String motehrName;
    private final String pushNotif;

    private EditProfileParam(Builder builder) {
        username = builder.username;
        email = builder.email;
        password = builder.password;
        name = builder.name;
        noTelp = builder.noTelp;
        nik = builder.nik;
        userImg = builder.userImg;
        dob = builder.dob;
        motehrName = builder.motehrName;
        pushNotif = builder.pushNotif;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EditProfileParam copy) {
        Builder builder = new Builder();
        builder.username = copy.username;
        builder.email = copy.email;
        builder.password = copy.password;
        builder.name = copy.name;
        builder.noTelp = copy.noTelp;
        builder.nik = copy.nik;
        builder.userImg = copy.userImg;
        builder.dob = copy.dob;
        builder.motehrName = copy.motehrName;
        builder.pushNotif = copy.pushNotif;
        return builder;
    }

    /* --------------------------------------------------- */
    /* > Getter */
    /* --------------------------------------------------- */

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getNik() {
        return nik;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getDob() {
        return dob;
    }

    public String getMotehrName() {
        return motehrName;
    }

    /* --------------------------------------------------- */
    /* > Builder */
    /* --------------------------------------------------- */


    public static final class Builder {
        private String username;
        private String email;
        private String password;
        private String name;
        private String noTelp;
        private String nik;
        private String userImg;
        private String dob;
        private String motehrName;
        private String pushNotif;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNoTelp(String noTelp) {
            this.noTelp = noTelp;
            return this;
        }

        public Builder withNik(String nik) {
            this.nik = nik;
            return this;
        }

        public Builder withUserImg(String userImg) {
            this.userImg = userImg;
            return this;
        }

        public Builder withDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder withMotehrName(String motehrName) {
            this.motehrName = motehrName;
            return this;
        }

        public Builder withPushNotif(String pushNotif) {
            this.pushNotif = pushNotif;
            return this;
        }

        public EditProfileParam build() {
            return new EditProfileParam(this);
        }
    }
}
