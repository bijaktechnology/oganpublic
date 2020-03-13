package com.incendiary.ambulanceapp.features.report.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.androidcommon.android.text.Strings;

public class ReportDetailProps implements Parcelable {

    private final Report report;

    public ReportDetailProps(Report report) {
        this.report = report;
    }

    public ReportDetailProps(String reportId) {
        this.report = new Report(reportId);
    }

    public Report getReport() {
        return report;
    }

    public boolean haveFullReport() {
        return !Strings.isEmpty(report.getFotoLaporan());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.report, flags);
    }

    protected ReportDetailProps(Parcel in) {
        this.report = in.readParcelable(Report.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReportDetailProps> CREATOR = new Parcelable.Creator<ReportDetailProps>() {
        @Override
        public ReportDetailProps createFromParcel(Parcel source) {
            return new ReportDetailProps(source);
        }

        @Override
        public ReportDetailProps[] newArray(int size) {
            return new ReportDetailProps[size];
        }
    };
}
