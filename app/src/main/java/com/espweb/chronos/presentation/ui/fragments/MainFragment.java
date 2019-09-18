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
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.presenters.impl.MainPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.CronogramaAdapter;
import com.espweb.chronos.presentation.ui.dialogs.CronogramaDialog;
import com.espweb.chronos.presentation.viewmodels.MainViewModel;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements MainPresenter.View {

    private MainPresenter mainPresenter;
    private CronogramaAdapter cronogramaAdapter;

    @BindView(R.id.rv_cronogramas)
    RecyclerView rvCronogramas;

    @BindView(R.id.cl_empty_cronogramas)
    ConstraintLayout clEmptyCronogramas;

    private MainViewModel mainViewModel;

    private long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
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

        mainViewModel.getUser().observe(this, user -> {
            userId = user.getId();
            mainPresenter.getAllCronogramas(userId);
        });

        mainViewModel.getCronogramas().observe(this, cronogramas -> {
            if(cronogramas != null) {
                showCronogramas(cronogramas);
            } else {
                cronogramaAdapter.clear();
            }
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
        Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository = new CronogramaRepositoryImpl(requireContext());
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository,
                new SessaoRepositoryImpl(requireContext()), mainViewModel);
    }

    private void initAdapter() {
        cronogramaAdapter = new CronogramaAdapter(requireContext());
    }

    private CronogramaAdapter.CronogramaListListener cronogramaListListener = cronograma -> {
        MainFragmentDirections.ActionMainToCronograma action = MainFragmentDirections.actionMainToCronograma();
        action.setIdCronograma(cronograma.getId());
        Navigation.findNavController(requireView()).navigate(action);
    };

    private void initRecyclerView() {
        cronogramaAdapter.setCronogramaListListener(cronogramaListListener);
        rvCronogramas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCronogramas.setAdapter(cronogramaAdapter);
        //cronogramaAdapter.registerAdapterDataObserver(new RecyclerViewDataObserver(rvCronogramas, clEmptyCronogramas));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void showCronogramas(List<Cronograma> cronogramas) {
        cronogramaAdapter.setCronogramas(cronogramas);
        showRecyclerView();
    }

    private void showRecyclerView() {
        clEmptyCronogramas.setVisibility(View.GONE);
        rvCronogramas.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        clEmptyCronogramas.setVisibility(View.VISIBLE);
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
}
