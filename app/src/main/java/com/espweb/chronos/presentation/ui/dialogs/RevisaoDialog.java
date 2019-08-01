package com.espweb.chronos.presentation.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.utils.DateUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevisaoDialog extends ArtefatoDialog<Revisao> {

    @BindView(R.id.rg_escopo)
    RadioGroup rgEscopo;

    private RevisaoDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_revisao, container);
        ButterKnife.bind(this, view);
        return view;
    }

    public static RevisaoDialog newInstance(Revisao revisao) {
        RevisaoDialog revisaoDialog = new RevisaoDialog();
        Bundle args = new Bundle();
        args.putParcelable("revisao", Parcels.wrap(revisao));
        revisaoDialog.setArguments(args);
        return revisaoDialog;
    }

    @Override
    public void buildFromArguments() {
        if(getArguments() != null)
            artefato = Parcels.unwrap(getArguments().getParcelable("revisao"));
    }

    @Override
    public void fillForm() {
        ((RadioButton)rgEscopo.getChildAt(artefato.getEscopo().getIntValue())).setChecked(true);
        tilDescricao.getEditText().setText(artefato.getDescricao());
        tilData.getEditText().setText(DateUtils.formatDate(artefato.getData()));
    }

    @Override
    public void setTitle() {
        int title = artefato.isNew() ? R.string.new_review : R.string.edit;
        tvTitle.setText(title);
    }

    @Override
    public void buildFromForm() {
        RadioButton checked = rgEscopo.findViewById(rgEscopo.getCheckedRadioButtonId());
        String descricao = tilDescricao.getEditText().getText().toString();
        String data = tilData.getEditText().getText().toString();
        artefato.setEscopo(Revisao.Escopo.fromInt(rgEscopo.indexOfChild(checked)));
        artefato.setDescricao(descricao);
        artefato.setData(DateUtils.parse(data));
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
