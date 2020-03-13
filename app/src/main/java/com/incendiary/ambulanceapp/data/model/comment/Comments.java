package com.incendiary.ambulanceapp.data.model.comment;

import com.incendiary.ambulanceapp.utils.Dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comments {

    private static final String COMMENT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getRelativeTime(Comment comment) {
        Date date = parseCommentTimestamp(comment);
        if (date == null) {
            return comment.getTimestamp();
        }
        return Dates.getRelativeTimeDisplayString(date.getTime());
    }

    private static Date parseCommentTimestamp(Comment comment) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(COMMENT_DATE_FORMAT, Locale.getDefault());
        try {
            return simpleDateFormat.parse(comment.getTimestamp());
        } catch (ParseException e) {
            com.incendiary.androidcommon.etc.Logger.log(e);
            return null;
        }
    }

}
