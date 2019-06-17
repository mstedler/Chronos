package com.espweb.chronos.presentation.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.presentation.ui.adapters.CronogramaAdapter;
import com.espweb.chronos.storage.CronogramaRepositoryImpl;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter.View;
import com.espweb.chronos.presentation.presenters.impl.MainPresenterImpl;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View, CronogramaAdapter.CronogramaListListener {
    private MainPresenter mainPresenter;
    private CronogramaAdapter cronogramaAdapter;

    @BindView(R.id.rv_cronogramas)
    RecyclerView rvCronogramas;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);

        CronogramaRepository cronogramaRepository = new CronogramaRepositoryImpl(this);
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository);

        cronogramaAdapter = new CronogramaAdapter(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        cronogramaAdapter.setCronogramaListListener(this);
        rvCronogramas.setLayoutManager(new LinearLayoutManager(this));
        rvCronogramas.setAdapter(cronogramaAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.resume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCronogramas(List<Cronograma> cronogramas) {
        cronogramaAdapter.setCronogramasList(cronogramas);
    }

    @Override
    public void onCronogramaClicked(Cronograma cronograma) {
        if(cronograma != null) {
            this.navigator.navigateToCronograma(this, cronograma.getId());
        }
    }
}
