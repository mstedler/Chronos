package com.espweb.chronos.presentation.ui.custom.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YesNoDialog extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    public interface YesNoDialogListener {
        void yesClicked();
    }

    private YesNoDialogListener yesNoDialogListener;

    private YesNoDialog() {
    }

    public static YesNoDialog newInstance(String title, String message) {
        YesNoDialog yesNoDialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        yesNoDialog.setArguments(args);
        return yesNoDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_yes_no, container);
        ButterKnife.bind(this, view);

        String title = getArguments().getString("title", "");
        String message = getArguments().getString("message", "");

        tvTitle.setText(title);
        tvMessage.setText(message);

        return view;
    }


    @OnClick(R.id.btn_yes)
    void onYesClick() {
        dismiss();
        yesNoDialogListener.yesClicked();
    }

    @OnClick(R.id.btn_no)
    void onNoClick() {
        dismiss();
    }

    public void setListener(YesNoDialogListener yesNoDialogListener) {
        this.yesNoDialogListener = yesNoDialogListener;
    }
}
