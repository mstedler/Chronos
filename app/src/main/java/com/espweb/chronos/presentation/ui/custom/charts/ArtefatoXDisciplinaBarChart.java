package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.custom.charts.base.GenericBarChart;

import java.util.List;

public class ArtefatoXDisciplinaBarChart extends GenericBarChart<Disciplina> {
    public ArtefatoXDisciplinaBarChart(Context context, String datasetLabel) {
        super(context, datasetLabel);
    }

    public ArtefatoXDisciplinaBarChart(Context context) {
        super(context);
    }

    public ArtefatoXDisciplinaBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtefatoXDisciplinaBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected float getEntryValue(Disciplina disciplina) {
        List<Assunto> assuntos = disciplina.getAssuntos();
        return assuntos.stream().map(Assunto::getArtefatosSize).mapToInt(Integer::intValue).sum();
    }

    @Override
    protected String getLabel(Disciplina disciplina) {
        return disciplina.getNome();
    }
}
