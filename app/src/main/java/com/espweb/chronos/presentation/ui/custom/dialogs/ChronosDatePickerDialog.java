package com.espweb.chronos.presentation.ui.custom.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

public class ChronosDatePickerDialog {
    private DatePickerDialog datePickerDialog;

    public ChronosDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        LocalDateTime localDateTime = LocalDateTime.now();
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        datePickerDialog.getDatePicker().setMinDate(zonedDateTime.toInstant().toEpochMilli());
    }

    public void show(){
        datePickerDialog.show();
    }
}
