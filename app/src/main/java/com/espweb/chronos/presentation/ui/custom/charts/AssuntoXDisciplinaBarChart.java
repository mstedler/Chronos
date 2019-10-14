package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.custom.charts.base.GenericBarChart;

public class AssuntoXDisciplinaBarChart extends GenericBarChart<Disciplina> {

    public AssuntoXDisciplinaBarChart(Context context) {
        super(context);
    }

    public AssuntoXDisciplinaBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AssuntoXDisciplinaBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected float getEntryValue(Disciplina disciplina) {
        return disciplina.getAssuntosSize();
    }

    @Override
    protected String getLabel(Disciplina disciplina) {
        return disciplina.getNome();
    }
}
