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
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.presenters.ResetPasswordPresenter;
import com.espweb.chronos.presentation.presenters.impl.ResetPasswordPresenterImpl;
import com.espweb.chronos.presentation.utils.EmailValidator;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordFragment extends Fragment implements ResetPasswordPresenter.View {

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private ResetPasswordPresenter resetPasswordPresenter;

    public ResetPasswordFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPasswordPresenter = new ResetPasswordPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new SessaoRepositoryImpl(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        Drawable close = requireContext().getDrawable(R.drawable.ic_arrow_back).mutate();
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

    @OnClick(R.id.btn_continuar)
    void onClick(){
        tilEmail.setError(null);
        String email = tilEmail.getEditText().getText().toString();
        resetPasswordPresenter.resetPassword(email);
    }

    @Override
    public void onInvalidEmail() {
        tilEmail.setError(getString(R.string.email_invalido));
    }

    @Override
    public void onRequestSent() {
        Toast.makeText(requireContext(), "Enviamos um email para que você possa definir uma nova senha.", Toast.LENGTH_LONG).show();
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void showProgress() {
        ViewUtils.makeWindowUntouchable(requireActivity().getWindow());
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        ViewUtils.makeWindowTouchable(requireActivity().getWindow());
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {

    }
}
