package com.espweb.chronos.presentation.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espweb.chronos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndamentoFragment extends Fragment {


    public AndamentoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_andamento, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_andamento_cronogramas)
    void onAndamentoCronogramaClick(){
        Navigation.findNavController(requireView()).navigate(R.id.action_andamento_to_charts_cronograma);
    }

    @OnClick(R.id.btn_andamento_disciplinas)
    void onAndamentoDisciplinasClick(){
        Navigation.findNavController(requireView()).navigate(R.id.action_andamento_to_charts_disciplina);
    }

}
