package com.espweb.chronos.presentation.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;

public class YesNoDialog extends DialogFragment implements Dialog.OnClickListener {

    public interface YesNoDialogListener {
        void yesClicked();
    }

    private YesNoDialogListener yesNoDialogListener;

    private YesNoDialog() {
    }

    public static YesNoDialog newInstance() {
        YesNoDialog yesNoDialog = new YesNoDialog();
        Bundle args = new Bundle();
        yesNoDialog.setArguments(args);
        return yesNoDialog;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(getContext());
        alertDialogBuiler.setTitle(R.string.are_you_sure);
        alertDialogBuiler.setPositiveButton(R.string.yes, this);
        alertDialogBuiler.setNegativeButton(R.string.no, this);
        return alertDialogBuiler.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
       if(which == Dialog.BUTTON_POSITIVE) {
           yesNoDialogListener.yesClicked();
       }
       dismiss();
    }

    public void setListener(YesNoDialogListener yesNoDialogListener) {
        this.yesNoDialogListener = yesNoDialogListener;
    }
}
