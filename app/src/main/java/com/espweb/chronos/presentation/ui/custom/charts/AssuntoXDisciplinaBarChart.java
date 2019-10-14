package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.custom.charts.base.GenericBarChart;

public class AssuntoXDisciplinaBarChart2 extends GenericBarChart<Disciplina> {

    public AssuntoXDisciplinaBarChart2(Context context) {
        super(context);
    }

    public AssuntoXDisciplinaBarChart2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AssuntoXDisciplinaBarChart2(Context context, AttributeSet attrs, int defStyle) {
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
