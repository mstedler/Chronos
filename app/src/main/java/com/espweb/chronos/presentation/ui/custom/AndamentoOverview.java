package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Overview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndamentoOverview extends CardView {

    @BindView(R.id.tv_qtd_assuntos)
    TextView tvQuantidadeAssuntos;

    @BindView(R.id.tv_qtd_disciplinas)
    TextView tvQuantidadeDisciplinas;

    @BindView(R.id.tv_qtd_diarias)
    TextView tvQuantidadeDiarias;

    @BindView(R.id.tv_qtd_semanais)
    TextView tvQuantidadeSemanais;

    @BindView(R.id.tv_qtd_quinzenais)
    TextView tvQuantidadeQuinzenais;

    @BindView(R.id.tv_qtd_mensais)
    TextView tvQuantidadeMensais;

    @BindView(R.id.tv_qtd_exercicios_acertos)
    TextView tvQuantidadeAcertosExercicios;

    @BindView(R.id.tv_qtd_exercicios_total)
    TextView tvQuantidadeExerciciosTotal;

    @BindView(R.id.tv_qtd_livros)
    TextView tvQuantidadeLivros;

    @BindView(R.id.tv_qtd_int_art_post)
    TextView tvQuantidadeIntArtPost;

    @BindView(R.id.tv_qtd_video_aula)
    TextView tvQuantidadeVideoAula;

    @BindView(R.id.tv_qtd_material_total)
    TextView tvQuantidadeMaterialTotal;

    public AndamentoOverview(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AndamentoOverview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AndamentoOverview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.overview, this);
        ButterKnife.bind(this, view);
    }

    public void bind(Overview overview) {
        tvQuantidadeDisciplinas.setText(String.valueOf(overview.getDisciplinas()));
        tvQuantidadeAssuntos.setText(String.valueOf(overview.getAssuntos()));

        tvQuantidadeLivros.setText(String.format(getContext().getString(R.string.minutos), overview.getLivros()));
        tvQuantidadeIntArtPost.setText(String.format(getContext().getString(R.string.minutos), overview.getIntArtPost()));
        tvQuantidadeVideoAula.setText(String.format(getContext().getString(R.string.minutos), overview.getVideoAulas()));
        tvQuantidadeMaterialTotal.setText(String.format(getContext().getString(R.string.minutos), overview.getTotalMateriais()));

        tvQuantidadeAcertosExercicios.setText(String.valueOf(overview.getAcertos()));
        tvQuantidadeExerciciosTotal.setText(String.valueOf(overview.getTotalExercicios()));

        tvQuantidadeDiarias.setText(String.valueOf(overview.getDiarias()));
        tvQuantidadeSemanais.setText(String.valueOf(overview.getSemanais()));
        tvQuantidadeQuinzenais.setText(String.valueOf(overview.getQuinzenais()));
        tvQuantidadeMensais.setText(String.valueOf(overview.getMensais()));
    }
}
