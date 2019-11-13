package com.espweb.chronos.presentation.ui.custom.charts.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;

import androidx.core.content.ContextCompat;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.ui.custom.charts.formatters.LabelFormatter;
import com.espweb.chronos.presentation.utils.ColorGenerator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericBarChart<T> extends BarChart {
    private ColorGenerator colorGenerator;
    private List<BarEntry> chartEntries;
    private List<Integer> colors;
    private SparseArray<String> xAxisLabels;

    private String datasetLabel;

    public GenericBarChart(Context context, String datasetLabel) {
        super(context);
        this.datasetLabel = datasetLabel;
    }

    public GenericBarChart(Context context) {
        super(context);
        build();
    }

    public GenericBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GenericBarChart,
                0, 0);

        try {
            datasetLabel = a.getString(R.styleable.GenericBarChart_datasetLabel);
        } finally {
            a.recycle();
        }
        build();
    }

    public GenericBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        build();
    }

    private void build() {
        colorGenerator = new ColorGenerator();
        chartEntries = new ArrayList<>();
        colors = new ArrayList<>();
        xAxisLabels = new SparseArray<>();
        setDrawBarShadow(false);
        setDrawValueAboveBar(true);
        getDescription().setEnabled(false);
        setNoDataText("Não há nada para mostrar");
        buildYAxis();
    }

    public void buildChart(List<T> objects) {
        chartEntries.clear();
        for (int i = 0; i < objects.size(); i++) {
            T object = objects.get(i);
            xAxisLabels.append(i, getLabel(object));
            chartEntries.add(new BarEntry(i, getEntryValue(object)));
            colors.add(colorGenerator.generateColor());
        }

        buildXAxis(xAxisLabels);
        BarDataSet dataSet = buildDataSet(chartEntries, colors);
        BarData data = buildBarData(dataSet);

        setData(data);
        invalidate();
        animateY(1000);
    }

    protected abstract float getEntryValue(T object);

    protected abstract String getLabel(T object);

    private BarDataSet buildDataSet(List<BarEntry> chartEntries, List<Integer> colors) {
        BarDataSet dataSet;
        if (getData() != null &&
                getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) getData().getDataSetByIndex(0);
            dataSet.setValues(chartEntries);
            getData().notifyDataChanged();
            notifyDataSetChanged();

        } else {
            dataSet = new BarDataSet(chartEntries, datasetLabel);
        }
        dataSet.setColors(colors);
        return dataSet;
    }

    private void buildXAxis(SparseArray<String> labels) {
        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new LabelFormatter(labels));
    }

    private void buildYAxis() {
        YAxis leftAxis = getAxisLeft();
        leftAxis.setValueFormatter(new DefaultValueFormatter(0));
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = getAxisRight();
        rightAxis.setValueFormatter(new DefaultValueFormatter(0));
        rightAxis.setGranularity(1f);
        rightAxis.setAxisMinimum(0f);
    }

    private BarData buildBarData(BarDataSet dataSet) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setHighlightEnabled(false);
        data.setValueTextSize(10f);
        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setBarWidth(0.1f);
        return data;
    }
}
