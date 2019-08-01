package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;

public class UnknownViewHolder extends ArtefatoViewHolder {


    public UnknownViewHolder(@NonNull View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        super(itemView, artefatoListListener);
    }

    @Override
    public void bind(Artefato artefato) {

    }

    @Override
    public void onClick(View v) {

    }
}
