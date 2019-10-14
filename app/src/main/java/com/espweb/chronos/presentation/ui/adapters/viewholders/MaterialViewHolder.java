package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialViewHolder extends ArtefatoViewHolder {

    private Material material;

    @BindView(R.id.tv_tipo_material)
    TextView tvTipoMaterial;

    @BindView(R.id.tv_data)
    TextView tvData;

    @BindView(R.id.tv_tempo)
    TextView tvTempo;

    @BindView(R.id.cl_container)
    ConstraintLayout container;

    @BindView(R.id.ll_behindviews)
    LinearLayout behindViews;

    private Context context;

    public MaterialViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    @Override
    public void bind(Artefato artefato) {
        material = (Material) artefato;
        String descricao = context.getResources().getStringArray(R.array.materials)[((Material) artefato).getEscopo().getIntValue() - 1];
        tvTipoMaterial.setText(descricao);
        tvData.setText(DateUtils.formatDate(material.getData()));
        int minutos = ((Material) artefato).getMinutos();
        String time = context.getResources().getQuantityString(R.plurals.tempo, minutos, minutos);
        tvTempo.setText(time);
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(getAdapterPosition(), material);
    }

    @Override
    public void hideBehindViews() {
        behindViews.setVisibility(View.GONE);
    }

    @Override
    public void showBehindViews() {
        behindViews.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public View getSwipeableContainerView() {
        return container;
    }
}
