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
import com.espweb.chronos.presentation.presenters.SignInPresenter;
import com.espweb.chronos.presentation.presenters.impl.SignInPresenterImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
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

    public interface SignInFragmentListener {
        void showSignUpFragment();
        void showMainActivity();
    }

    private SignInFragmentListener signInFragmentListener;
    private SignInPresenter signInPresenter;


    public SignInFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        Fragment signInFragment = new SignInFragment();
        return signInFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessaoRepository sessaoRepository = new SessaoRepositoryImpl(getContext());
        signInPresenter = new SignInPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, sessaoRepository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            signInFragmentListener = (SignInFragmentListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement SignInFragmentListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        signInPresenter.resume();
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainActivity() {
        signInFragmentListener.showMainActivity();
    }

    @Override
    public void setEmailError() {
        tilEmail.setError(getString(R.string.cannot_be_empty));
        tilEmail.requestFocus();
    }

    @Override
    public void setPasswordError() {
        tilPassword.setError(getString(R.string.cannot_be_empty));
        tilPassword.requestFocus();
    }

    @Override
    public void clearErrors() {
        tilEmail.setError(null);
        tilPassword.setError(null);
    }

    @Override
    public void showWelcomeMessage(User user) {
        Toast.makeText(getContext(), getString(R.string.welcome) + " " + user.getName(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_show_signUp)
    void onClick(){
        signInFragmentListener.showSignUpFragment();
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInClick() {
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        signInPresenter.signInUser(email, password);
    }
}
