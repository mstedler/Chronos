package com.espweb.chronos.presentation.ui.fragments.charts;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espweb.chronos.R;

import butterknife.ButterKnife;

public class DisciplinaAssuntoChartFragment extends Fragment {



    public DisciplinaAssuntoChartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disciplina_assunto_chart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


}
