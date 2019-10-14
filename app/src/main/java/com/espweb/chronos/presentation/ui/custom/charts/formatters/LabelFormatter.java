package com.espweb.chronos.presentation.ui.custom.charts.formatters;

import android.util.SparseArray;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class LabelFormatter extends ValueFormatter {

    private SparseArray<String> labels;

    public LabelFormatter(SparseArray<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value) {
        String label = labels.get((int) value);
        return label == null ? "" : label;
    }
}
