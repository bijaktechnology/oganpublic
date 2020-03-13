package com.incendiary.ambulanceapp.data.model;

import com.google.gson.annotations.SerializedName;

public class BookingResponse extends BaseResponse {

    /**
     * booking_id : 13
     * user_id : 2
     * booking_status : W
     */

    @SerializedName("booking_detail") private BookingDetail bookingDetail;

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public static class BookingDetail {
        @SerializedName("booking_id") private String bookingId;
        @SerializedName("user_id") private String userId;
        @SerializedName("booking_status") private String bookingStatus;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }
    }
}
