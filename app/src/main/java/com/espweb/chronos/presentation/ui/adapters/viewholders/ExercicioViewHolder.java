package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExercicioViewHolder extends ArtefatoViewHolder {
    //@BindView(R.id.)
    //TextView tvDescricao;

    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_placar)
    TextView tvPlacar;
    @BindView(R.id.cl_container)
    ConstraintLayout container;

    @BindView(R.id.ll_behindviews)
    LinearLayout behindViews;

    private Exercicio exercicio;

    public ExercicioViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        StringBuilder placar = new StringBuilder();
        this.exercicio = (Exercicio) artefato;
        //tvDescricao.setText(exercicio.getDescricao());
        placar.append(exercicio.getAcertos());
        placar.append("/");
        placar.append(exercicio.getQuantidade());
        tvPlacar.setText(placar.toString());
        tvData.setText(DateUtils.formatDate(artefato.getData()));
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(getAdapterPosition(), exercicio);
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
