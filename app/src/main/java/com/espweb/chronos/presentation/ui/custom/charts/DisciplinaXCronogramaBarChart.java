package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.ui.custom.charts.base.GenericBarChart;
import com.espweb.chronos.presentation.ui.custom.charts.formatters.LabelFormatter;
import com.espweb.chronos.presentation.utils.ColorGenerator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaXCronogramaBarChart extends GenericBarChart<Cronograma> {

    public DisciplinaXCronogramaBarChart(Context context, String datasetLabel) {
        super(context, datasetLabel);
    }

    public DisciplinaXCronogramaBarChart(Context context) {
        super(context);
    }

    public DisciplinaXCronogramaBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisciplinaXCronogramaBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected float getEntryValue(Cronograma cronograma) {
        return cronograma.getDisciplinas().size();
    }

    @Override
    protected String getLabel(Cronograma cronograma) {
        return cronograma.getTitulo();
    }
}
