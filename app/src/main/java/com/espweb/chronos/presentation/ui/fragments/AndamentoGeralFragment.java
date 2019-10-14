package com.espweb.chronos.presentation.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.AndamentoGeralPresenter;
import com.espweb.chronos.presentation.presenters.impl.AndamentoGeralPresenterImpl;
import com.espweb.chronos.presentation.ui.custom.EmptyView;
import com.espweb.chronos.presentation.ui.custom.charts.ArtefatoCountBarChart;
import com.espweb.chronos.presentation.ui.custom.charts.AssuntoXCronogramaBarChart;
import com.espweb.chronos.presentation.ui.custom.charts.DisciplinaXCronogramaBarChart;
import com.espweb.chronos.presentation.utils.ChartUtil;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndamentoGeralFragment extends Fragment implements AndamentoGeralPresenter.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ev_cronogramas)
    EmptyView evCronogramas;

    @BindView(R.id.sv_charts)
    ScrollView svCharts;

    @BindView(R.id.chart_disciplina_x_cronograma)
    DisciplinaXCronogramaBarChart disciplinaXCronogramaBarChart;

    @BindView(R.id.chart_assunto_x_cronograma)
    AssuntoXCronogramaBarChart assuntoXCronogramaBarChart;

    @BindView(R.id.chart_artefato_x_cronograma)
    ArtefatoCountBarChart artefatoCountBarChart;

    private UserViewModel userViewModel;

    private AndamentoGeralPresenter andamentoGeralPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        andamentoGeralPresenter = new AndamentoGeralPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new CronogramaRepositoryImpl(requireContext()));
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_andamento_geral, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        userViewModel.getUser().observe(this, user -> {
            andamentoGeralPresenter.getAllCronogramas(user.getId(), false);
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

    @Override
    public void showEmptyView() {
        evCronogramas.setVisibility(View.VISIBLE);
        svCharts.setVisibility(View.GONE);
    }

    @Override
    public void setCronogramas(List<Cronograma> cronogramas) {
        disciplinaXCronogramaBarChart.buildChart(cronogramas);
        assuntoXCronogramaBarChart.buildChart(cronogramas);
        artefatoCountBarChart.buildChart(ChartUtil.mapArtefatoFromCronogramas(cronogramas));
    }

    @Override
    public void showCharts() {
        evCronogramas.setVisibility(View.GONE);
        svCharts.setVisibility(View.VISIBLE);
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
