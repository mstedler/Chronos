package com.espweb.chronos.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.ui.utils.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisciplinaDialog extends DialogFragment {

    private long cronogramaId;

    private long disciplinaId;
    private String nome;


    public interface DisciplinaDialogListener {
        void createDisciplina(long cronogramaId, String nome);
        void updateDisciplina(long disciplinaId, String nome);
    }

    @BindView(R.id.til_nome)
    TextInputLayout tilNome;

    private DisciplinaDialogListener disciplinaDialogListener;

    private DisciplinaDialog() {

    }

    public static DisciplinaDialog newInstance(long cronogramaId, Disciplina disciplina) {
        DisciplinaDialog disciplinaDialog = new DisciplinaDialog();
        Bundle args = new Bundle();
        args.putLong("idCronograma", cronogramaId);
        if(disciplina != null) {
            args.putLong("idDisciplina", disciplina.getId());
            args.putString("nomeDisciplina", disciplina.getNome());
        }
        disciplinaDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TitledDialog);
        disciplinaDialog.setArguments(args);
        return disciplinaDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            disciplinaDialogListener = (DisciplinaDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DisciplinaDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_disciplina, container);
        ButterKnife.bind(this, view);

        cronogramaId = getArguments().getLong("idCronograma", -1);

        if(cronogramaId == -1) {
            Toast.makeText(getContext(), R.string.cronograma_not_found, Toast.LENGTH_SHORT).show();
            return null;
        }



        disciplinaId = getArguments().getLong("idDisciplina", -1);
        nome = getArguments().getString("nomeDisciplina", "");


        int title = R.string.create_subject;
        if(disciplinaId > 0) {
            tilNome.getEditText().setText(nome);
            title = R.string.edit;
        }

        getDialog().setTitle(title);

        return view;

    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick(){
        String nome = tilNome.getEditText().getText().toString().trim();

        if(nome.isEmpty()) {
            tilNome.setError(getString(R.string.cannot_be_empty));
            return;
        }

        if(disciplinaId > 0) {
            disciplinaDialogListener.updateDisciplina(disciplinaId, nome);
        } else {
            disciplinaDialogListener.createDisciplina(cronogramaId, nome);
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_cancelar)
    public void cancel() {
        getDialog().dismiss();
    }
}
