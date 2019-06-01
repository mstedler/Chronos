package com.espweb.chronos.presentation.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.storage.CronogramaRepositoryImpl;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter.View;
import com.espweb.chronos.presentation.presenters.impl.MainPresenterImpl;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View {

    private MainPresenter mainPresenter;
    private CronogramaRepository cronogramaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cronogramaRepository = new CronogramaRepositoryImpl(this);
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository);
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

    }


}
