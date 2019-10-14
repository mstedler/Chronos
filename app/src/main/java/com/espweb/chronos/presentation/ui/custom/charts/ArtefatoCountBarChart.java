package com.espweb.chronos.presentation.ui.custom.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.presentation.ui.custom.charts.formatters.LabelFormatter;
import com.espweb.chronos.presentation.utils.ColorGenerator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArtefatoCountBarChart extends BarChart {

    public ArtefatoCountBarChart(Context context) {
        super(context);
        build();
    }

    public ArtefatoCountBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        build();
    }

    public ArtefatoCountBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        build();
    }

    private void build() {
        setDrawBarShadow(false);
        setDrawValueAboveBar(true);
        getDescription().setEnabled(false);

        buildYAxis();
        buildLegend();
    }

    private void buildLegend() {
        Legend l = getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXOffset(0f);
        l.setTextSize(8f);
    }

    public void buildChart(Map<String, Map<EnumTipo, Long>> mapArtefatoGroupCount) {
        SparseArray<String> xAxisLabels = new SparseArray<>();
        List<BarEntry> exercicios = new ArrayList<>();
        List<BarEntry> materiais = new ArrayList<>();
        List<BarEntry> revisoes = new ArrayList<>();

        float groupSpace = 0.55f;
        float barSpace = 0.05f;
        float barWidth = 0.1f;
        // (0.1 + 0.05) * 3 + 0.55 = 1.00 -> intervalo por "grupo"

        int groupCount = mapArtefatoGroupCount.size();
        int i = 0;

        for(Map.Entry<String, Map<EnumTipo, Long>> entry : mapArtefatoGroupCount.entrySet()) {
            exercicios.add(new BarEntry(i, entry.getValue().getOrDefault(EnumTipo.EXERCICIO, 0L)));
            revisoes.add(new BarEntry(i, entry.getValue().getOrDefault(EnumTipo.REVISAO, 0L)));
            materiais.add(new BarEntry(i, entry.getValue().getOrDefault(EnumTipo.MATERIAL, 0L)));
            xAxisLabels.append(i, entry.getKey());
            i++;
        }

        List<IBarDataSet> dataSets = buildDataSet(exercicios, revisoes, materiais);

        BarData data = buildBarData(dataSets);
        data.setBarWidth(barWidth);
        setData(data);

        buildXAxis(xAxisLabels, getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        groupBars(0, groupSpace, barSpace);

        invalidate();
        animateY(1000);
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

    private BarData buildBarData(List<IBarDataSet> dataSets) {
        BarData data = new BarData(dataSets);
        data.setHighlightEnabled(false);
        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextSize(10f);
        return data;
    }

    private void buildXAxis(SparseArray<String> xAxisLabels, float axisMaximum) {
        XAxis xAxis = getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(axisMaximum);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new LabelFormatter(xAxisLabels));
    }

    private List<IBarDataSet> buildDataSet(List<BarEntry> exercicios, List<BarEntry> revisoes, List<BarEntry> materiais) {
        ColorGenerator colorGenerator = new ColorGenerator();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        BarDataSet setExercicio, setMaterial, setRevisao;

        if (getData() != null && getData().getDataSetCount() > 0) {

            setExercicio = (BarDataSet) getData().getDataSetByIndex(0);
            setRevisao = (BarDataSet) getData().getDataSetByIndex(1);
            setMaterial = (BarDataSet) getData().getDataSetByIndex(2);
            setExercicio.setValues(exercicios);
            setRevisao.setValues(revisoes);
            setMaterial.setValues(materiais);
            getData().notifyDataChanged();
            notifyDataSetChanged();

        } else {
            setExercicio = new BarDataSet(exercicios, "Exercícios");
            setExercicio.setColor(colorGenerator.generateColor());
            setRevisao = new BarDataSet(revisoes, "Revisões");
            setRevisao.setColor(colorGenerator.generateColor());
            setMaterial = new BarDataSet(materiais, "Materiais");
            setMaterial.setColor(colorGenerator.generateColor());
        }

        dataSets.add(setExercicio);
        dataSets.add(setRevisao);
        dataSets.add(setMaterial);

        return dataSets;
    }
}
