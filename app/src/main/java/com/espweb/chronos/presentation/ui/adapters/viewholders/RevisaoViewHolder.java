package com.espweb.chronos.presentation.ui.adapters.viewholders;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevisaoViewHolder extends ArtefatoViewHolder {

    private Revisao revisao;

    @BindView(R.id.tv_escopo)
    TextView tvEscopo;

    @BindView(R.id.tv_data)
    TextView tvData;

    @BindView(R.id.cl_container)
    ConstraintLayout container;

    @BindView(R.id.ll_behindviews)
    LinearLayout behindViews;

    public RevisaoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        this.revisao = (Revisao) artefato;
        String[] escopos = itemView.getContext().getResources().getStringArray(R.array.escopos);
        tvEscopo.setText(escopos[((Revisao) artefato).getEscopo().getIntValue()]);
        tvData.setText(DateUtils.formatDate(revisao.getData()));
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(getAdapterPosition(), revisao);
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
