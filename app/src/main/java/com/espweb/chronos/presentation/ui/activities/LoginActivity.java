package com.espweb.chronos.presentation.ui.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.presenters.LoginPresenter;
import com.espweb.chronos.presentation.presenters.impl.LoginPresenterImpl;
import com.espweb.chronos.presentation.ui.fragments.SignInFragment;
import com.espweb.chronos.presentation.ui.fragments.SignUpFragment;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.threading.MainThreadImpl;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginPresenter.View,
        SignInFragment.SignInFragmentListener,
        SignUpFragment.SignUpFragmentListener {

    private static final String TAG = "BaseActivity";
    private LoginPresenter loginPresenter;

    private Fragment signInFragment;
    private SignUpFragment signUpFragment;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SessaoRepository sessaoRepository = new SessaoRepositoryImpl(this);
        loginPresenter = new LoginPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, sessaoRepository);
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.resume();
    }

    @Override
    public void showSignInView() {
        changeFragment(signInFragment, false, false);
    }

    @Override
    public void showSignUpView() {

        changeFragment(signUpFragment, true, true);
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

    private void changeFragment(Fragment frag, boolean saveInBackstack, boolean animate) {
        String backStateName = ((Object) frag).getClass().getName();

        try {
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {

                FragmentTransaction transaction = manager.beginTransaction();

                if (animate) {
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
                }

                transaction.replace(R.id.fragment_container, frag, backStateName);

                if (saveInBackstack) {
                    transaction.addToBackStack(backStateName);
                }

                transaction.commit();
            } else {
                manager.popBackStackImmediate();
            }
        } catch (IllegalStateException exception) {
            Log.w(TAG, exception.toString());
        }
    }

    @Override
    public void showSignUpFragment() {
        loginPresenter.showSignUp();
    }

    @Override
    public void showSignInFragment() {
        loginPresenter.showSignIn();
    }

    @Override
    public void showMainActivity() {
        navigator.navigateToMain(this);
        finish();
    }
}
