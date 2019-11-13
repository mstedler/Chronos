package com.espweb.chronos.presentation.ui.custom.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.ui.custom.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialDialog extends ArtefatoDialog<Material> {

    @BindView(R.id.til_minutos)
    TextInputLayout tilMinutos;

    @BindView(R.id.spn_escopo)
    Spinner spnEscopo;

    @BindView(R.id.comecar_agora)
    CheckBox cbComecarAgora;

    public MaterialDialog() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_material, container);
        ButterKnife.bind(this, view);
        return view;
    }

    private Spinner.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            artefato.setEscopo(Material.Escopo.fromInt(pos+1));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.materials, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnEscopo.setAdapter(adapter);
        spnEscopo.setOnItemSelectedListener(itemSelectedListener);
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
        if (getArguments() != null)
            artefato = Parcels.unwrap(getArguments().getParcelable("material"));
    }

    @Override
    public void fillForm() {
        int minutos = artefato.getMinutos();
        boolean edit = minutos > 0;
        tilMinutos.getEditText().setText(edit ? String.valueOf(minutos) : null);
        tilData.getEditText().setText(DateUtils.formatDate(artefato.getData()));
        spnEscopo.setSelection(artefato.getEscopo().getIntValue() - 1);
        tilDescricao.getEditText().setText(artefato.getDescricao());
        if(edit) {
            cbComecarAgora.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitle() {
        int title = artefato.isNew() ? R.string.novo_material : R.string.editar;
        tvTitle.setText(title);
    }

    @Override
    protected boolean validate() {
        clearErrors();
        if (!artefato.isMinutosValid()) {
            tilMinutos.setError(getString(R.string.maior_que_0));
            return false;
        }
        return true;
    }

    private void clearErrors() {
        tilDescricao.setError(null);
        tilMinutos.setError(null);
    }

    @Override
    public void buildFromForm() {
        String tempo = tilMinutos.getEditText().getText().toString();
        String data = tilData.getEditText().getText().toString();
        String descricao = tilDescricao.getEditText().getText().toString();
        boolean comecarAgora = cbComecarAgora.isChecked();
        artefato.setComecarAgora(comecarAgora);

        artefato.setData(DateUtils.parse(data));
        artefato.setDescricao(descricao);
        try {
            artefato.setMinutos(Integer.valueOf(tempo));
        } catch (NumberFormatException e) {
            artefato.setMinutos(0);
        }
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
