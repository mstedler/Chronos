package com.espweb.chronos.presentation.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.presentation.ui.utils.DateUtils;
import com.espweb.chronos.presentation.ui.utils.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class CronogramaDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.til_titulo)
    TextInputLayout tilTitulo;

    @BindView(R.id.til_descricao)
    TextInputLayout tilDescricao;

    @BindView(R.id.til_data_inicial)
    TextInputLayout tilDataInicial;

    @BindView(R.id.til_data_final)
    TextInputLayout tilDataFinal;


    private long id;

    public interface CronogramaDialogListener {
        void createCronograma(String titulo, String descricao, Date inicio, Date fim);
        void updateCronograma(long id, String titulo, String descricao, Date inicio, Date fim);
    }

    private CronogramaDialogListener cronogramaDialogListener;

    private CronogramaDialog() {

    }

    public static CronogramaDialog newInstance(Cronograma cronograma) {
        CronogramaDialog cronogramaDialog = new CronogramaDialog();
        Bundle args = new Bundle();

        if(cronograma != null) {
            args.putLong("id", cronograma.getId());
            args.putString("titulo", cronograma.getTitulo());
            args.putString("descricao", cronograma.getDescricao());
            args.putString("inicio", DateUtils.formatDate(cronograma.getInicio()));
            args.putString("fim", DateUtils.formatDate(cronograma.getFim()));
        }

        cronogramaDialog.setStyle(STYLE_NORMAL, R.style.TitledDialog);
        cronogramaDialog.setArguments(args);
        return cronogramaDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_cronograma, container);
        ButterKnife.bind(this, view);

        id = getArguments().getLong("id", -1);

        int title = R.string.create_cronograma;

        if(id != -1) {
            String titulo = getArguments().getString("titulo", "");
            String descricao = getArguments().getString("descricao", "");
            String inicio = getArguments().getString("inicio", "");
            String fim = getArguments().getString("fim", "");

            tilTitulo.getEditText().setText(titulo);
            tilDescricao.getEditText().setText(descricao);
            tilDataInicial.getEditText().setText(inicio);
            tilDataFinal.getEditText().setText(fim);

            title = R.string.edit;
        }

        getDialog().setTitle(getString(title));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            cronogramaDialogListener = (CronogramaDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Context must implement CronogramaDialogListener");
        }
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_save)
    void saveClicked(){
        if(id == -1) {
            //cronogramaDialogListener.createCronograma();
        }
    }

    @OnClick(R.id.btn_cancelar)
    void cancelarClicked() {
        getDialog().dismiss();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @OnClick({R.id.til_data_inicial, R.id.til_data_final})
    public void onClick(View v) {

    }
}
