package com.espweb.chronos.presentation.ui.fragments;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.presentation.presenters.SignInPresenter;
import com.espweb.chronos.presentation.presenters.impl.SignInPresenterImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignInFragment extends Fragment implements SignInPresenter.View {
    private static final String TAG = "SignInFragment";

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @BindView(R.id.btn_show_signUp)
    Button btnShowSignUp;


    private UserViewModel userViewModel;

    private SignInPresenter signInPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInPresenter = new SignInPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, new SessaoRepositoryImpl(requireContext()), userViewModel);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    public void blockClick() {
        btnShowSignUp.setClickable(false);
        btnSignIn.setClickable(false);
    }

    public void allowClick(){
        btnShowSignUp.setClickable(true);
        btnSignIn.setClickable(true);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEmailError() {
        tilEmail.setError(getString(R.string.email_invalido));
        tilEmail.requestFocus();
    }

    @Override
    public void setPasswordError() {
        tilPassword.setError(getString(R.string.no_minimo_6));
        tilPassword.requestFocus();
    }

    @Override
    public void clearErrors() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    @Override
    public void showWelcomeMessage(User user) {
    }

    @OnClick(R.id.btn_show_signUp)
    void onClick(){
        Navigation.findNavController(requireView()).navigate(R.id.action_signin_to_signup);
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInClick() {
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        signInPresenter.signInUser(email, password);
    }

    @OnClick(R.id.btn_forgot_passsword)
    void onForgotPasswordClick() {
        Navigation.findNavController(requireView()).navigate(R.id.action_signin_to_resetpassword);
    }

    @Override
    public void showMain() {
        SignInFragmentDirections.ActionSigninToMain actionSigninToMain = SignInFragmentDirections.actionSigninToMain();
        actionSigninToMain.setFreshStart(true);
        Navigation.findNavController(requireView()).navigate(actionSigninToMain);
    }
}
