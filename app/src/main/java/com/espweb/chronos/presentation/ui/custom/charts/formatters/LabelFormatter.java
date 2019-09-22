package com.espweb.chronos.presentation.ui.custom.charts.formatters;

import android.util.SparseArray;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.HashMap;

public class CronogramaFormatter extends ValueFormatter {

    private SparseArray<String> cronogramaPos;

    public CronogramaFormatter(SparseArray<String> cronogramaPos) {
        this.cronogramaPos = cronogramaPos;
    }

    @Override
    public String getFormattedValue(float value) {
        return cronogramaPos.get((int) value);
    }
}
