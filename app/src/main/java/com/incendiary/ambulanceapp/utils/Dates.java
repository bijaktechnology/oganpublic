package com.incendiary.ambulanceapp.utils;

import android.text.format.DateUtils;

import com.incendiary.androidcommon.etc.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {

    private static final String DOB_FORMAT = "yyyy-MM-dd";

    public static Calendar parseDob(String rawDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DOB_FORMAT, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(rawDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            Logger.log(e);
            return null;
        }
    }

    public static String formatDob(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DOB_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getRelativeTimeDisplayString(long reportTime) {
        long now = System.currentTimeMillis();
        long difference = now - reportTime;
        return (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS) ?
                "Beberapa detik yang lalu" :
                (String) DateUtils.getRelativeTimeSpanString(
                        reportTime,
                        now,
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE);
    }
}
