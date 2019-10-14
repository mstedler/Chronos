package com.espweb.chronos.presentation.ui.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.AndamentoPorCronogramaPresenter;
import com.espweb.chronos.presentation.presenters.impl.AndamentoPorCronogramaPresenterImpl;
import com.espweb.chronos.presentation.ui.custom.AndamentoOverview;
import com.espweb.chronos.presentation.ui.custom.EmptyView;
import com.espweb.chronos.presentation.ui.custom.charts.ArtefatoCountBarChart;
import com.espweb.chronos.presentation.ui.custom.charts.AssuntoXDisciplinaBarChart;
import com.espweb.chronos.presentation.utils.ChartUtil;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndamentoPorCronogramaFragment extends Fragment implements AndamentoPorCronogramaPresenter.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ev_cronogramas)
    EmptyView evCronogramas;

    @BindView(R.id.sv_charts)
    ScrollView svCharts;

    @BindView(R.id.chart_assunto_x_disciplina)
    AssuntoXDisciplinaBarChart assuntoXDisciplinaBarChart;

    @BindView(R.id.chart_artefato_x_disciplina)
    ArtefatoCountBarChart artefatoXDisciplinaBarChart;

    @BindView(R.id.spinner_cronogramas)
    Spinner spinnerCronogramas;

    @BindView(R.id.andamento_overview)
    AndamentoOverview andamentoOverview;

    private UserViewModel userViewModel;

    private AndamentoPorCronogramaPresenter andamentoPorCronogramaPresenter;

    public AndamentoPorCronogramaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        andamentoPorCronogramaPresenter = new AndamentoPorCronogramaPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new CronogramaRepositoryImpl(requireContext()));
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_andamento_por_cronograma, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        userViewModel.getUser().observe(this, user -> {
            andamentoPorCronogramaPresenter.getAllCronogramas(user.getId(), false);
        });
    }

    private void initToolbar() {
        Drawable close = requireContext().getDrawable(R.drawable.ic_close).mutate();
        Drawable drawable = DrawableCompat.wrap(close);
        DrawableCompat.setTint(
                drawable,
                ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        );
        toolbar.setTitle("");
        toolbar.setNavigationIcon(drawable);
        AppCompatActivity appCompatActivity = (AppCompatActivity) requireActivity();
        appCompatActivity.setSupportActionBar(toolbar);
    }

    private Spinner.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            Cronograma cronograma = (Cronograma) adapterView.getItemAtPosition(pos);
            andamentoOverview.bind(ChartUtil.buildOverview(cronograma));
            List<Disciplina> disciplinas = cronograma.getDisciplinas();
            assuntoXDisciplinaBarChart.buildChart(disciplinas);
            artefatoXDisciplinaBarChart.buildChart(ChartUtil.mapArtefatoFromDisciplinas(disciplinas));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void buildSpinner(List<Cronograma> cronogramas) {
        ArrayAdapter<Cronograma> cronogramaArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, cronogramas);
        spinnerCronogramas.setAdapter(cronogramaArrayAdapter);
        spinnerCronogramas.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public void setCronogramas(List<Cronograma> cronogramas) {
        buildSpinner(cronogramas);
    }

    @Override
    public void showCharts() {
        svCharts.setVisibility(View.VISIBLE);
        evCronogramas.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        evCronogramas.setVisibility(View.VISIBLE);
        svCharts.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
