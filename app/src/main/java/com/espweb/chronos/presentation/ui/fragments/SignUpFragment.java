package com.espweb.chronos.presentation.ui.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.SignUpPresenter;
import com.espweb.chronos.presentation.presenters.impl.SignUpPresenterImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.data.UserRepositoryImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    private static final String TAG = "SignUpFragment";

    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private SignUpPresenter signUpPresenter;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpPresenter = new SignUpPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new SessaoRepositoryImpl(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
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

    @OnClick(R.id.btn_show_signIn)
    void onClick() {
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void signUpSuccess() {
        //Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void signUpFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNameError() {
        tilName.setError(getString(R.string.nao_pode_ser_vazio));
        tilName.requestFocus();
    }

    @Override
    public void showEmailError() {
        tilEmail.setError(getString(R.string.email_invalido));
        tilEmail.requestFocus();
    }

    @Override
    public void showPasswordError() {
        tilPassword.setError(getString(R.string.no_minimo_6));
        tilPassword.requestFocus();
    }

    @Override
    public void clearErrors() {
        tilEmail.setError(null);
        tilName.setError(null);
        tilPassword.setError(null);
    }

    @OnClick(R.id.btn_sign_up)
    void onSignUpClick(){
        String name = tilName.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        signUpPresenter.signUp(name, email, password);
    }
}
