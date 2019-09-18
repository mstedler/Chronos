package com.espweb.chronos.network.converters;

import android.text.format.DateFormat;

import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static String format(Date date) {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return DateFormat.format("yyyy-MM-dd", date).toString();
    }
}
