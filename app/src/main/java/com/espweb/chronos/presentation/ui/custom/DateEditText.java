package com.espweb.chronos.presentation.ui.custom;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import com.espweb.chronos.presentation.ui.dialogs.ChronosDatePickerDialog;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.threeten.bp.LocalDate;

public class DateEditText extends TextInputEditText implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ChronosDatePickerDialog chronosDatePickerDialog;

    public DateEditText(Context context) {
        super(context);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(false);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(chronosDatePickerDialog == null)
            chronosDatePickerDialog = new ChronosDatePickerDialog(getContext(), this);

        chronosDatePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
        setText(DateUtils.formatDate(getContext(), localDate));
    }
}
