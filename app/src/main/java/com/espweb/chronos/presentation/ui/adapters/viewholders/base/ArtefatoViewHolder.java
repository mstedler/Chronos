package com.espweb.chronos.presentation.ui.adapters.viewholders.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;

public abstract class ArtefatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ArtefatoAdapter.ArtefatoListListener artefatoListListener;

    protected ArtefatoViewHolder(@NonNull View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.artefatoListListener = artefatoListListener;
    }

    public abstract void bind(Artefato artefato);
}
