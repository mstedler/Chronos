package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.custom.charts.base.GenericBarChart;

import java.util.List;

public class AssuntoXCronogramaBarChart extends GenericBarChart<Cronograma> {
    public AssuntoXCronogramaBarChart(Context context, String datasetLabel) {
        super(context, datasetLabel);
    }

    public AssuntoXCronogramaBarChart(Context context) {
        super(context);
    }

    public AssuntoXCronogramaBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AssuntoXCronogramaBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected float getEntryValue(Cronograma cronograma) {
        List<Disciplina> disciplinas = cronograma.getDisciplinas();
        return disciplinas.stream().map(Disciplina::getAssuntosSize).mapToInt(Integer::intValue).sum();
    }

    @Override
    protected String getLabel(Cronograma cronograma) {
        return cronograma.getTitulo();
    }
}
