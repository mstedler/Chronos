package com.espweb.chronos.presentation.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExercicioDialog extends ArtefatoDialog<Exercicio>  {

    @BindView(R.id.til_acertos)
    TextInputLayout tilAcertos;

    @BindView(R.id.til_quantidade)
    TextInputLayout tilQuantidade;

    private ExercicioDialog() {

    }

    public static ExercicioDialog newInstance(Exercicio exercicio){
        ExercicioDialog exercicioDialog = new ExercicioDialog();
        Bundle args = new Bundle();
        args.putParcelable("exercicio", Parcels.wrap(exercicio));
        exercicioDialog.setArguments(args);
        return  exercicioDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exercicio, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void buildFromArguments() {
        if(getArguments() != null)
            artefato = Parcels.unwrap(getArguments().getParcelable("exercicio"));
    }

    @Override
    public void fillForm() {
        tilDescricao.getEditText().setText(artefato.getDescricao());
        tilAcertos.getEditText().setText(artefato.getAcertos() + "");
        tilQuantidade.getEditText().setText(artefato.getQuantidade() + "");
        tilData.getEditText().setText(DateUtils.formatDate(artefato.getData()));
    }

    @Override
    public void setTitle() {
        int title = artefato.isNew() ? R.string.novo_exercicio : R.string.editar;
        tvTitle.setText(getText(title));
    }

    @Override
    protected boolean validate() {
        clearErrors();
        if(!artefato.isValid()) {
            tilQuantidade.setError("");
            tilAcertos.setError(getString(R.string.menor_ou_igual));
            return false;
        }
        if(!artefato.isDescricaoValid()) {
            tilDescricao.setError(getString(R.string.deve_ter_mais_que_3));
            return false;
        }
        return true;
    }

    private void clearErrors() {
        tilQuantidade.setError(null);
        tilDescricao.setError(null);
        tilAcertos.setError(null);
    }


    @Override
    public void buildFromForm() {
        String data = tilData.getEditText().getText().toString();
        String descricao = tilDescricao.getEditText().getText().toString();
        String quantidade = tilQuantidade.getEditText().getText().toString();
        String acertos = tilAcertos.getEditText().getText().toString();

        int iQuantidade = quantidade.isEmpty() ? 0 : Integer.valueOf(quantidade);
        int iAcertos = acertos.isEmpty() ? 0 : Integer.valueOf(acertos);
        artefato.setData(DateUtils.parse(data));
        artefato.setDescricao(descricao);
        artefato.setQuantidade(iQuantidade);
        artefato.setAcertos(iAcertos);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
