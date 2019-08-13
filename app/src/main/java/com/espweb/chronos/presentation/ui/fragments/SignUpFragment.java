package com.espweb.chronos.presentation.ui.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
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

    public interface SignUpFragmentListener {
        void showSignInFragment();
    }

    private SignUpFragmentListener signUpFragmentListener;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessaoRepository sessaoRepository = new SessaoRepositoryImpl(getContext());
        Repository<User> userRepository = new UserRepositoryImpl(getContext());
        signUpPresenter = new SignUpPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, sessaoRepository, userRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            signUpFragmentListener = (SignUpFragmentListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement SignUpFragmentListener");
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        ViewUtils.makeWindowUntouchable(getActivity().getWindow());
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        ViewUtils.makeWindowTouchable(getActivity().getWindow());
    }

    @Override
    public void showError(String message) {

    }

    @OnClick(R.id.btn_show_signIn)
    void onClick() {
        signUpFragmentListener.showSignInFragment();
    }

    @Override
    public void signUpSuccess() {
        signUpFragmentListener.showSignInFragment();
    }

    @Override
    public void signUpFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNameError() {
        tilName.setError(getString(R.string.cannot_be_empty));
        tilName.requestFocus();
    }

    @Override
    public void showEmailError() {
        tilEmail.setError(getString(R.string.cannot_be_empty));
        tilEmail.requestFocus();
    }

    @Override
    public void showPasswordError() {
        tilPassword.setError(getString(R.string.cannot_be_empty));
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
