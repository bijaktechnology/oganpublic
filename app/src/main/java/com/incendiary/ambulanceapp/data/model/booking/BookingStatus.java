package com.incendiary.ambulanceapp.data.model.booking;

import com.google.gson.annotations.SerializedName;

public class BookingStatus {

    public static BookingStatus EMPTY_BOOKING = new BookingStatus();

    /**
     * user_id : 1
     * booking_ambulance : {"booking_id":"61","booking_status":true}
     */

    @SerializedName("user_id") private String userId;
    @SerializedName("booking_ambulance") private Booking bookingAmbulance;
    @SerializedName("booking_dokter") private Booking bookingDoctor;
    @SerializedName("booking_bidan") private Booking bookingBidan;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Booking getBookingAmbulance() {
        return bookingAmbulance;
    }

    public Booking getBookingDoctor() {
        return bookingDoctor;
    }

    public Booking getBookingBidan() {
        return bookingBidan;
    }

    public static class Booking {
        /**
         * booking_id : 61
         * booking_status : true
         */

        @SerializedName("booking_id") private String bookingId;
        @SerializedName("booking_status") private boolean bookingStatus;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public boolean isBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(boolean bookingStatus) {
            this.bookingStatus = bookingStatus;
        }
    }
}
