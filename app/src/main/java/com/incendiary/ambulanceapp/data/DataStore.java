package com.incendiary.ambulanceapp.data;

import android.support.annotation.Nullable;
import android.util.Log;

import com.esafirm.imagepicker.model.Image;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.androidcommon.etc.Logger;
import com.orhanobut.hawk.Hawk;

import rx.Observable;

public class DataStore {

    private class Key {
        static final String PROFILE = "Key.Profile";
        static final String KEYCODE = "Key.KeyCode";

        /* Booking */
        static final String BOOKING_ID = "Key.BookingID";

        /* Schema */
        static final String SCHEMA_VERSION = "Key.SchemaVersion";

        /* Report Image */
        static final String REPORT_IMAGE = "Key.ReportImage";
    }

    private static final int LAST_SCHEMA_VERSION = 0;
    private static final int CURRENT_SCHEMA_VERSION = LAST_SCHEMA_VERSION + 1;

    /* --------------------------------------------------- */
    /* > Schema */
    /* --------------------------------------------------- */

    public static void clearStoreIfNeeded() {
        if (Hawk.get(Key.SCHEMA_VERSION, LAST_SCHEMA_VERSION) < CURRENT_SCHEMA_VERSION) {
            Hawk.clear();
            Hawk.put(Key.SCHEMA_VERSION, CURRENT_SCHEMA_VERSION);
        }
    }

    /* --------------------------------------------------- */
    /* > Account */
    /* --------------------------------------------------- */

    public static String getKeyCode() {
        return Hawk.get(Key.KEYCODE);
    }

    private static void setKeyCode(String keyCode) {
        put(Key.KEYCODE, keyCode);
    }

    public static void saveProfile(Profile profile) {
        put(Key.PROFILE, profile);
        setKeyCode(profile.getKeycode());
    }

    public static Observable<Profile> getProfile() {
        return get(Key.PROFILE);
    }

    public static void clearProfile() {
        Hawk.remove(Key.PROFILE);
        Hawk.remove(Key.KEYCODE);
    }

    /* --------------------------------------------------- */
    /* > Booking Status */
    /* --------------------------------------------------- */

    public static void setActiveBookingId(String bookingId, int mapType) {
        put(Key.BOOKING_ID + mapType, bookingId);
    }

    /* --------------------------------------------------- */
    /* > Report Image */
    /* --------------------------------------------------- */

    public static void saveReportImage(Image image) {
        put(Key.REPORT_IMAGE, image);
    }

    @Nullable
    public static Observable<Image> getReportImage() {
        return get(Key.REPORT_IMAGE);
    }

    /* --------------------------------------------------- */
    /* > Convenience */
    /* --------------------------------------------------- */

    private static <T> Observable<T> get(String key) {
        return Hawk.getObservable(key);
    }

    private static void put(String key, Object value) {
        Hawk.putObservable(key, value)
                .doOnError(throwable -> {
                    Logger.log(throwable);
                    Logger.log(Log.INFO, "Put error @ " + key);
                })
                .onErrorReturn(null)
                .subscribe();
    }
}
