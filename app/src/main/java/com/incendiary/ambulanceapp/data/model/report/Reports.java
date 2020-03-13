package com.incendiary.ambulanceapp.data.model.report;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.Dates;
import com.incendiary.androidcommon.android.text.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reports {

    private static final String REPORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getRelativeTime(Report report) {
        try {
            return Dates.getRelativeTimeDisplayString(parseReportDate(report.getReportTime()).getTime());
        } catch (Exception e) {
            return report.getReportTime();
        }
    }

    public static Date parseReportDate(String rawDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(REPORT_DATE_FORMAT, Locale.getDefault());
        try {
            return simpleDateFormat.parse(rawDate);
        } catch (ParseException e) {
            ErrorHandler.handleError(e);
            return null;
        }
    }

    public static String getReportLocation(Report report) {
        if (report == null || report.getReportLocation() == null) {
            return "Lokasi tidak dikethaui";
        }

        final ReportLocation reportLocation = report.getReportLocation();
        if (!Strings.isEmpty(reportLocation.getStreet())) {
            return reportLocation.getStreet().trim();
        }
        if (!Strings.isEmpty(reportLocation.getCity())) {
            return reportLocation.getCity().trim();
        }
        if (!Strings.isEmpty(reportLocation.getProvince())) {
            return reportLocation.getProvince().trim();
        }
        return "Lokasi tidak dikethaui";
    }

    @StringRes
    public static int getStatusText(Report report) {
        ReportStatus reportStatus = report.getStatusLaporan();
        switch (reportStatus) {
            case COMPLETE:
                return R.string.status_done;
            case PROCESSING:
                return R.string.status_process;
            default:
                return R.string.status_wait;
        }
    }


    @DrawableRes
    public static int getStatusBackground(Report report) {
        ReportStatus reportStatus = report.getStatusLaporan();
        switch (reportStatus) {
            case COMPLETE:
                return R.drawable.bg_status_done;
            case PROCESSING:
                return R.drawable.bg_status_process;
            default:
                return R.drawable.bg_status_wait;
        }
    }

}
