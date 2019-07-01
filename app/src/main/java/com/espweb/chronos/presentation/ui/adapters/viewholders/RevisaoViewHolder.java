package com.espweb.chronos.presentation.ui.adapters.viewholders;

import androidx.annotation.NonNull;

import android.view.View;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;

import butterknife.ButterKnife;

public class RevisaoViewHolder extends ArtefatoViewHolder {

    private Revisao revisao;

    public RevisaoViewHolder(@NonNull View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        super(itemView, artefatoListListener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        this.revisao = (Revisao) artefato;
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(revisao);
    }
}
