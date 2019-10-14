package com.espweb.chronos.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.ProfilePresenter;
import com.espweb.chronos.presentation.presenters.impl.ProfilePresenterImpl;
import com.espweb.chronos.presentation.ui.custom.dialogs.ProfileDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.YesNoDialog;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements ProfilePresenter.View {

    @BindView(R.id.tv_nome)
    TextView tvNome;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.tv_cronograma)
    TextView tvCronograma;

    @BindView(R.id.tv_qtd_cronogramas)
    TextView tvQtdCronogramas;

    private UserViewModel userViewModel;

    private ProfilePresenter profilePresenter;

    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profilePresenter = new ProfilePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new SessaoRepositoryImpl(requireContext()),
                new CronogramaRepositoryImpl(requireContext()));
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel.getUser().observe(this, user -> {
            this.user = user;
            updateView(user);
            profilePresenter.getCronogramaCount(user.getId());
        });
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
    public void setCronogramaCount(int count) {
        String cronograma = requireContext().getResources().getQuantityString(R.plurals.cronograma, count, count);
        tvQtdCronogramas.setText(String.valueOf(count));
        tvCronograma.setText(cronograma);
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

    private ProfileDialog.ProfileDialogListener profileDialogListener = user -> {
        this.user = user;
        updateView(user);
    };

    private void updateView(User user) {
        tvNome.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }

    @OnClick(R.id.iv_edit_nome)
    void onEditNomeClick() {
        ProfileDialog profileDialog = ProfileDialog.newInstance(user);
        profileDialog.setProfileDialogListener(profileDialogListener);
        profileDialog.show(requireFragmentManager(), "PROFILE_DIALOG");
    }
}
