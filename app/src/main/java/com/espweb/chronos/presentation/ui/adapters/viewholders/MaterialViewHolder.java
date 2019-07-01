package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;

import butterknife.ButterKnife;

public class MaterialViewHolder extends ArtefatoViewHolder {

    private Material material;

    public MaterialViewHolder(@NonNull View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        super(itemView, artefatoListListener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        this.material = (Material) artefato;
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked((Artefato)v.getTag());
    }
}
