package com.espweb.chronos.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.espweb.chronos.R;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.presenters.ProfilePresenter;
import com.espweb.chronos.presentation.presenters.impl.ProfilePresenterImpl;
import com.espweb.chronos.presentation.ui.dialogs.YesNoDialog;
import com.espweb.chronos.threading.MainThreadImpl;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements ProfilePresenter.View {
    private ProfilePresenter profilePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter = new ProfilePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new SessaoRepositoryImpl(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private YesNoDialog.YesNoDialogListener yesNoDialogListener = () -> {
        profilePresenter.logout();
    };

    @OnClick(R.id.logout)
    void showConfirmLogoutDialog() {
        YesNoDialog logoutDialog = YesNoDialog.newInstance(getString(R.string.sair), getString(R.string.tem_certeza));
        logoutDialog.setListener(yesNoDialogListener);
        logoutDialog.show(requireFragmentManager(), "LOGOUT_DIALOG");

    }

    @Override
    public void showLoginView() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profile_to_signin);
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
