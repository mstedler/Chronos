package com.espweb.chronos.presentation.ui.custom;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.espweb.chronos.presentation.ui.dialogs.ChronosTimePickerDialog;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;

public class TimeEditText extends TextInputEditText implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private ChronosTimePickerDialog chronosTimePickerDialog;

    public TimeEditText(Context context) {
        super(context);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(false);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(chronosTimePickerDialog == null)
            chronosTimePickerDialog = new ChronosTimePickerDialog(getContext(), this);

        chronosTimePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        setText(DateUtils.formatTime(hourOfDay, minute));
    }
}
