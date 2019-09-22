package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.espweb.chronos.presentation.model.Cronograma;
import com.github.mikephil.charting.charts.BarChart;

import java.util.List;
import java.util.Random;

public class DisciplinaXCronogramaBarChart extends BarChart {
    private float colorH;

    public DisciplinaXCronogramaBarChart(Context context) {
        super(context);
        build();
    }

    public DisciplinaXCronogramaBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        build();
    }

    public DisciplinaXCronogramaBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        build();
    }

    private void build() {
        setDrawBarShadow(false);
        setDrawValueAboveBar(true);
    }


    public void buildChart(List<Cronograma> cronogramas) {
        Random random = new Random();
        colorH = random.nextFloat();

        
    }
}
