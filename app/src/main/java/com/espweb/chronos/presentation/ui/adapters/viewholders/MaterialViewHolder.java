package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialViewHolder extends ArtefatoViewHolder {

    private Material material;

    @BindView(R.id.tv_descricao)
    TextView tvDescricao;

    @BindView(R.id.tv_data)
    TextView tvData;

    @BindView(R.id.tv_tempo)
    TextView tvTempo;

    public MaterialViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        material = (Material) artefato;
        tvDescricao.setText(material.getDescricao());
        tvData.setText(DateUtils.formatDate(material.getData()));
        tvTempo.setText(DateUtils.formatMinutes(material.getMinutos()));
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(getAdapterPosition(), material);
    }
}
