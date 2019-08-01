package com.espweb.chronos.presentation.ui.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;

public class ChronosTimePickerDialog {
    private TimePickerDialog timePickerDialog;

    public ChronosTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener){
        timePickerDialog = new TimePickerDialog(context, listener, 0, 0, true);
    }

    public void show(){
        timePickerDialog.show();;
    }
}
