package com.espweb.chronos.presentation.ui.adapters.viewholders.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.presentation.ui.adapters.viewholders.ExercicioViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.MaterialViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.RevisaoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;

public class ArtefatoViewHolderFactory {

    public static ArtefatoViewHolder createViewHolder(ViewGroup parent, EnumTipo enumTipo) {
        final View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (enumTipo) {
            case MATERIAL:
                view = layoutInflater.inflate(R.layout.row_material, parent, false);
                return new MaterialViewHolder(view);
            case EXERCICIO:
                view = layoutInflater.inflate(R.layout.row_exercicio, parent, false);
                return new ExercicioViewHolder(view);
            case REVISAO:
                view = layoutInflater.inflate(R.layout.row_revisao, parent, false);
                return   new RevisaoViewHolder(view);
        }
        return new MaterialViewHolder(new View(parent.getContext()));
    }
}
