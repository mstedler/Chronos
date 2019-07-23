package com.espweb.chronos.presentation.ui.activities;

import android.os.Bundle;
import android.os.Handler;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.SplashPresenter;
import com.espweb.chronos.presentation.presenters.impl.SplashPresenterImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.threading.MainThreadImpl;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SessaoRepository sessaoRepository = new SessaoRepositoryImpl(this);
        SplashPresenter splashPresenter = new SplashPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, sessaoRepository);

        new Handler().postDelayed(() -> {
            splashPresenter.checkSessao();
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void navigateToMain() {
        navigator.navigateToMain(this);
        finish();
    }

    @Override
    public void navigateToLogin() {
        navigator.navigateToLogin(this);
        finish();
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
