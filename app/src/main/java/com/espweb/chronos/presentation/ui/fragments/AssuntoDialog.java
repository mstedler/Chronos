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
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.ui.utils.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssuntoDialog extends DialogFragment {

    private long disciplinaId;

    public interface CreateAssuntoDialogListener {
        void createAssunto(long disciplinaId, String descricao, String anotacao);
    }

    private CreateAssuntoDialogListener createAssuntoDialogListener;

    @BindView(R.id.til_anotacao)
    TextInputLayout tilAnotacao;

    @BindView(R.id.til_descricao)
    TextInputLayout tilDescricao;

    private AssuntoDialog() {

    }

    private static Bundle build(Disciplina disciplina, Assunto assunto) {
        Bundle args = new Bundle();
        args.putLong("idDisciplina", disciplina.getId());
        args.putString("nomeDisciplina", disciplina.getNome());

        if(assunto != null) {
            args.putString("descricaoAssunto", assunto.getDescricao());
            args.putString("anotacaoAssunto", assunto.getAnotacao());
        }
        return args;
    }

    public static AssuntoDialog newInstance(Disciplina disciplina, Assunto assunto) {
        AssuntoDialog assuntoDialog = new AssuntoDialog();
        Bundle args = build(disciplina, assunto);
        assuntoDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TitledDialog);
        assuntoDialog.setArguments(args);
        return assuntoDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_assunto, container, false);
        this.disciplinaId = getArguments().getLong("idDisciplina", -1);

        if(disciplinaId == -1) {
            Toast.makeText(getContext(), R.string.disciplina_not_found, Toast.LENGTH_SHORT).show();
            return null;
        }

        ButterKnife.bind(this, view);

        String descricao = getArguments().getString("descricaoAssunto", "");
        String anotacao = getArguments().getString("anotacaoAssunto", "");
        String nomeDisciplina = getArguments().getString("nomeDisciplina", "");


        int title = R.string.create_topic;
        if(!descricao.isEmpty()) {
            title = R.string.edit_topic;
            tilDescricao.getEditText().setText(descricao);
            tilAnotacao.getEditText().setText(anotacao);
        }

        getDialog().setTitle(getString(title) + " - " + nomeDisciplina);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            createAssuntoDialogListener = (CreateAssuntoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AuthenticationDialogListener");
        }
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_save)
    public void onSaveClick() {
        String descricao = tilDescricao.getEditText().getText().toString().trim();
        String anotacao = tilAnotacao.getEditText().getText().toString().trim();

        if(descricao.isEmpty()) {
            tilDescricao.setError(getContext().getString(R.string.cannot_be_empty));
            return;
        }
        createAssuntoDialogListener.createAssunto(disciplinaId, descricao, anotacao);
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_cancelar)
    public void cancel() {
        getDialog().dismiss();
    }
}
