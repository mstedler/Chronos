package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExercicioViewHolder extends ArtefatoViewHolder {
    @BindView(R.id.tv_descricao_exercicio)
    TextView tvDescricao;

    @BindView(R.id.tv_placar)
    TextView tvPlacar;

    private Exercicio exercicio;

    public ExercicioViewHolder(View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        super(itemView, artefatoListListener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Artefato artefato) {
        StringBuilder placar = new StringBuilder();
        this.exercicio = (Exercicio) artefato;
        tvDescricao.setText(exercicio.getDescricao());
        placar.append(exercicio.getAcertos());
        placar.append("/");
        placar.append(exercicio.getQuantidade());
        tvPlacar.setText(placar.toString());
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(exercicio);
    }
}
