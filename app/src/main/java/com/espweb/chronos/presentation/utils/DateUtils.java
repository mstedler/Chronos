package com.espweb.chronos.presentation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final String TAG = "DateUtils";

    public static String formatDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }
    public static Date parse(String from) {
        try {
            return DateFormat.getDateInstance(DateFormat.SHORT).parse(from);
        } catch (ParseException e) {
            return Calendar.getInstance().getTime();
        }
    }
}
