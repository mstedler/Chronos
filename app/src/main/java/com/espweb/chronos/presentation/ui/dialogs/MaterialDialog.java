package com.espweb.chronos.presentation.ui.dialogs;

import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;
import org.threeten.bp.LocalTime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialDialog extends ArtefatoDialog<Material> {

    @BindView(R.id.til_minutos)
    TextInputLayout tilMinutos;

    public MaterialDialog() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_material, container);
        ButterKnife.bind(this, view);
        return view;
    }

    public static MaterialDialog newInstance(Material material) {
        MaterialDialog materialDialog = new MaterialDialog();
        Bundle args = new Bundle();
        args.putParcelable("material", Parcels.wrap(material));
        materialDialog.setArguments(args);
        return materialDialog;
    }

    @Override
    public void buildFromArguments() {
        if(getArguments() != null)
            artefato = Parcels.unwrap(getArguments().getParcelable("material"));
    }

    @Override
    public void fillForm() {
        tilMinutos.getEditText().setText(DateUtils.formatMinutes(artefato.getMinutos()));
        tilData.getEditText().setText(DateUtils.formatDate(artefato.getData()));
        tilDescricao.getEditText().setText(artefato.getDescricao());
    }

    @Override
    public void setTitle() {
        int title = artefato.isNew() ? R.string.new_material : R.string.edit;
        tvTitle.setText(title);
    }

    @Override
    public void buildFromForm() {
        String tempo = tilMinutos.getEditText().getText().toString();

        LocalTime localTime = LocalTime.parse(tempo);

        String data = tilData.getEditText().getText().toString();
        String descricao = tilDescricao.getEditText().getText().toString();
        artefato.setData(DateUtils.parse(data));
        artefato.setDescricao(descricao);
        artefato.setMinutos(DateUtils.minutesOf(localTime));
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
