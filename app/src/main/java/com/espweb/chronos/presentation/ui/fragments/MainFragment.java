package com.espweb.chronos.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.presenters.impl.MainPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.CronogramaAdapter;
import com.espweb.chronos.presentation.ui.custom.EmptyView;
import com.espweb.chronos.presentation.ui.custom.dialogs.CronogramaDialog;
import com.espweb.chronos.presentation.viewmodels.UserViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements MainPresenter.View, CronogramaAdapter.CronogramaListListener {

    private MainPresenter mainPresenter;
    private CronogramaAdapter cronogramaAdapter;

    @BindView(R.id.rv_cronogramas)
    RecyclerView rvCronogramas;

    @BindView(R.id.ev_cronogramas)
    EmptyView evCronogramas;

    @BindView(R.id.loading)
    ConstraintLayout clLoading;

    private UserViewModel userViewModel;

    private long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initAdapter();
        initRecyclerView();
        boolean freshStart = MainFragmentArgs.fromBundle(requireArguments()).getFreshStart();
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            userId = user.getId();
            mainPresenter.getAllCronogramas(userId, freshStart);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void initPresenter() {
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                new CronogramaRepositoryImpl(requireContext()),
                new SessaoRepositoryImpl(requireContext()));
    }

    private void initAdapter() {
        cronogramaAdapter = new CronogramaAdapter(requireContext());
    }


    private void initRecyclerView() {
        cronogramaAdapter.setCronogramaListListener(this);
        rvCronogramas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCronogramas.setAdapter(cronogramaAdapter);
    }

    @Override
    public void showProgress() {
        clLoading.setVisibility(View.VISIBLE);
        evCronogramas.setVisibility(View.GONE);
        rvCronogramas.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        clLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
    }

    @Override
    public void showCronogramas(List<Cronograma> cronogramas) {
        cronogramaAdapter.setCronogramas(cronogramas);
    }

    @Override
    public void showRecyclerView() {
        evCronogramas.setVisibility(View.GONE);
        rvCronogramas.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        evCronogramas.setVisibility(View.VISIBLE);
        rvCronogramas.setVisibility(View.GONE);
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.signin_dest);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private CronogramaDialog.CronogramaDialogListener cronogramaDialogListener = new CronogramaDialog.CronogramaDialogListener() {
        @Override
        public void onCronogramaCreated(Cronograma cronograma) {
            cronogramaAdapter.addCronograma(cronograma);
            showRecyclerView();
        }

        @Override
        public void onCronogramaUpdated(Cronograma cronograma) {
        }
    };

    @OnClick(R.id.fab_add_cronograma)
    void addCronogramaClick() {
        CronogramaDialog cronogramaDialog = CronogramaDialog.newInstance(new Cronograma(userId));
        cronogramaDialog.setListener(cronogramaDialogListener);
        cronogramaDialog.show(requireFragmentManager(), "CRONOGRAMA_ADD_DIALOG");
    }

    @Override
    public void onCronogramaClicked(Cronograma cronograma) {
        MainFragmentDirections.ActionMainToCronograma action = MainFragmentDirections.actionMainToCronograma();
        action.setIdCronograma(cronograma.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}
