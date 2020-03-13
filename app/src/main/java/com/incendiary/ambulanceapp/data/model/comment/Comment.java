package com.incendiary.ambulanceapp.data.model.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {

    /**
     * comment_id : 1
     * user_id : 1
     * username : usertest
     * nama_lengkap : user test
     * comment : tes komentar
     * timestamp : 2017-05-14 12:04:20
     */

    @SerializedName("comment_id") private String commentId;
    @SerializedName("user_id") private String userId;
    @SerializedName("username") private String username;
    @SerializedName("nama_lengkap") private String namaLengkap;
    @SerializedName("comment") private String comment;
    @SerializedName("timestamp") private String timestamp;
    @SerializedName("user_img") private String avatar;
    @SerializedName("foto_laporan") private String photo;
    @SerializedName("laporan_id") private String reportId;

    public String getReportId() {
        return reportId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Comment() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentId);
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.namaLengkap);
        dest.writeString(this.comment);
        dest.writeString(this.timestamp);
        dest.writeString(this.avatar);
        dest.writeString(this.photo);
        dest.writeString(this.reportId);
    }

    protected Comment(Parcel in) {
        this.commentId = in.readString();
        this.userId = in.readString();
        this.username = in.readString();
        this.namaLengkap = in.readString();
        this.comment = in.readString();
        this.timestamp = in.readString();
        this.avatar = in.readString();
        this.photo = in.readString();
        this.reportId = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
