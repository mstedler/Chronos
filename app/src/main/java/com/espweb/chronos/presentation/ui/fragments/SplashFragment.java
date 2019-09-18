package com.espweb.chronos.presentation.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espweb.chronos.R;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.presenters.SplashPresenter;
import com.espweb.chronos.presentation.presenters.impl.SplashPresenterImpl;
import com.espweb.chronos.presentation.viewmodels.MainViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment implements SplashPresenter.View {

    private static int SPLASH_TIME_OUT = 1000;
    private MainViewModel mainViewModel;

    private SplashPresenter splashPresenter;
    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        initPresenter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> {
            splashPresenter.getSessao();
        }, SPLASH_TIME_OUT);
    }

    private void initPresenter() {
        splashPresenter = new SplashPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new SessaoRepositoryImpl(requireContext()),
                mainViewModel);
    }

    @Override
    public void navigateToMain() {
        navigateTo(R.id.action_splash_to_main);
    }

    @Override
    public void navigateToLogin() {
        navigateTo(R.id.action_splash_to_sign_in);
    }

    private void navigateTo(int action) {
        Navigation.findNavController(requireView()).navigate(action);
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
