package com.espweb.chronos.presentation.utils;

import android.content.Context;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final String TAG = "DateUtils";

    public static String formatDate(Date date) {
        if(date == null)
            date = new Date();

        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }
    public static Date parse(String from) {
        try {
            return DateFormat.getDateInstance(DateFormat.SHORT).parse(from);
        } catch (ParseException e) {
            return Calendar.getInstance().getTime();
        }
    }

    public static String formatMinutes(int minutos) {
        int hour = minutos / 60;
        int min = minutos % 60;
        LocalTime localTime = LocalTime.of(hour, min);
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String formatDate(Context context, LocalDate localDate) {
        Format dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String pattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static int minutesOf(LocalTime localTime) {
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    public static String formatTime(int hourOfDay, int minute) {
        return formatMinutes(hourOfDay * 60 + minute);
    }
}
