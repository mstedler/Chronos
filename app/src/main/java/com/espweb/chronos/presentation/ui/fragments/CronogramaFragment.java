package com.espweb.chronos.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.data.DisciplinaRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.impl.CronogramaPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter;
import com.espweb.chronos.presentation.ui.custom.EmptyView;
import com.espweb.chronos.presentation.ui.custom.dialogs.AssuntoDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.CronogramaDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.DisciplinaDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.YesNoDialog;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaFragment extends Fragment implements CronogramaPresenter.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_disciplinas)
    RecyclerView rvDisciplinas;

    @BindView(R.id.fab_add_disciplina)
    FloatingActionButton fabAddDisciplina;

    @BindView(R.id.ev_disciplinas)
    EmptyView evDisciplinas;

    private CronogramaPresenter cronogramaPresenter;
    private DisciplinaAdapter disciplinaAdapter;

    private Cronograma cronograma;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            showCronogramaDialog();
        } else if (item.getItemId() == R.id.item_delete) {
            showDeleteDialog(confirmDeleteCronograma);
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cronograma, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        long id = CronogramaFragmentArgs.fromBundle(requireArguments()).getIdCronograma();
        cronogramaPresenter.getCronograma(id);
    }


    private void init() {
        initPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initPresenter() {
        Context context = requireContext();
        cronogramaPresenter = new CronogramaPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                new CronogramaRepositoryImpl(context),
                new DisciplinaRepositoryImpl(context));
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close);
        AppCompatActivity appCompatActivity = (AppCompatActivity) requireActivity();
        appCompatActivity.setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        RecyclerViewExpandableItemManager expandableItemManager = new RecyclerViewExpandableItemManager(null);

        buildAdapter(expandableItemManager);

        RecyclerView.Adapter wrappedAdapter = expandableItemManager.createWrappedAdapter(disciplinaAdapter);
        RecyclerViewSwipeManager swipeManager = new RecyclerViewSwipeManager();
        wrappedAdapter = swipeManager.createWrappedAdapter(wrappedAdapter);

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();
        animator.setSupportsChangeAnimations(false);

        rvDisciplinas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDisciplinas.setItemAnimator(animator);
        rvDisciplinas.setAdapter(wrappedAdapter);
        rvDisciplinas.setHasFixedSize(false);

        rvDisciplinas.addItemDecoration(new SimpleListDividerDecorator(requireContext().getDrawable(R.drawable.list_divider), true));

        swipeManager.attachRecyclerView(rvDisciplinas);
        expandableItemManager.attachRecyclerView(rvDisciplinas);

        rvDisciplinas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fabAddDisciplina.hide();
                } else {
                    fabAddDisciplina.show();
                }
            }
        });
    }

    private void buildAdapter(RecyclerViewExpandableItemManager expandableItemManager) {
        disciplinaAdapter = new DisciplinaAdapter(requireContext(), expandableItemManager);
        disciplinaAdapter.setDisciplinaListListener(disciplinaListListener);
        disciplinaAdapter.setAssuntoListListener(assuntoListListener);
    }

    private DisciplinaAdapter.DisciplinaListListener disciplinaListListener = new DisciplinaAdapter.DisciplinaListListener() {
        @Override
        public void onEditDisciplinaClicked(Disciplina disciplina) {
            showDisciplinaDialog(disciplina);
        }

        @Override
        public void onDeleteDisciplinaClicked(Disciplina disciplina) {
            showDeleteDialog(() -> cronogramaPresenter.deleteDisciplina(disciplina));
        }

        @Override
        public void onCreateAssuntoClicked(Disciplina disciplina) {
            showAssuntoDialog(new Assunto(disciplina.getId()));
        }
    };


    private DisciplinaAdapter.AssuntoListListener assuntoListListener = assunto -> {
        CronogramaFragmentDirections.ActionCronogramaToAssunto action = CronogramaFragmentDirections.actionCronogramaToAssunto();
        action.setIdAssunto(assunto.getId());
        Navigation.findNavController(requireView()).navigate(action);
    };


    @Override
    public void setCronograma(com.espweb.chronos.presentation.model.Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    @Override
    public void bindCronogramaToView() {
        toolbar.setTitle(cronograma.getTitulo());
    }

    @Override
    public void showDisciplinas(List<com.espweb.chronos.presentation.model.Disciplina> disciplinas) {
        disciplinaAdapter.setDisciplinas(disciplinas);
        showRecyclerView();
    }

    @Override
    public void onCronogramaDeleted() {
        navigateToMain();
    }

    @Override
    public void navigateToMain() {
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void showEmptyView() {
        evDisciplinas.setVisibility(View.VISIBLE);
        rvDisciplinas.setVisibility(View.GONE);
    }

    private void showRecyclerView() {
        rvDisciplinas.setVisibility(View.VISIBLE);
        evDisciplinas.setVisibility(View.GONE);
    }

    private CronogramaDialog.CronogramaDialogListener cronogramaDialogListener = new CronogramaDialog.CronogramaDialogListener() {
        @Override
        public void onCronogramaCreated(com.espweb.chronos.presentation.model.Cronograma cronograma) {

        }

        @Override
        public void onCronogramaUpdated(com.espweb.chronos.presentation.model.Cronograma cronograma) {
            setCronograma(cronograma);
            bindCronogramaToView();
        }
    };

    private void showCronogramaDialog() {
        CronogramaDialog cronogramaDialog = CronogramaDialog.newInstance(cronograma);
        cronogramaDialog.setListener(cronogramaDialogListener);
        cronogramaDialog.show(requireFragmentManager(), "CRONOGRAMA_DIALOG");
    }

    private AssuntoDialog.AssuntoDialogListener assuntoDialogListener = new AssuntoDialog.AssuntoDialogListener() {
        @Override
        public void onAssuntoCreated(Assunto assunto) {
            disciplinaAdapter.addAssunto(assunto);
        }

        @Override
        public void onAssuntoUpdated(Assunto assunto) {

        }
    };

    private void showAssuntoDialog(Assunto assunto) {
        AssuntoDialog assuntoDialog = AssuntoDialog.newInstance(assunto);
        assuntoDialog.setAssuntoDialogListener(assuntoDialogListener);
        assuntoDialog.show(requireFragmentManager(), "ASSUNTO_DIALOG");
    }

    private YesNoDialog.YesNoDialogListener confirmDeleteCronograma = new YesNoDialog.YesNoDialogListener() {
        @Override
        public void yesClicked() {
            cronogramaPresenter.deleteCronograma(cronograma);
        }
    };

    private void showDeleteDialog(YesNoDialog.YesNoDialogListener listener) {
        YesNoDialog confirmDelete = YesNoDialog.newInstance(getString(R.string.excluir), getString(R.string.tem_certeza));
        confirmDelete.setListener(listener);
        confirmDelete.show(requireFragmentManager(), "YES_NO_DIALOG");
    }


    @Override
    public void onDisciplinaDeleted() {
        disciplinaAdapter.removeDisciplina();

        if(disciplinaAdapter.getGroupCount() == 0)
            showEmptyView();
    }

    private void showDisciplinaDialog(Disciplina disciplina) {
        DisciplinaDialog disciplinaDialog = DisciplinaDialog.newInstance(disciplina);
        disciplinaDialog.setListener(disciplinaDialogListener);
        disciplinaDialog.show(requireFragmentManager(), "DISCIPLINA_DIALOG");
    }

    private DisciplinaDialog.DisciplinaDialogListener disciplinaDialogListener = new DisciplinaDialog.DisciplinaDialogListener() {
        @Override
        public void onDisciplinaCreated(Disciplina disciplina) {
            disciplinaAdapter.addDisciplina(disciplina);
            showRecyclerView();
        }

        @Override
        public void onDisciplinaUpdated(Disciplina disciplina) {
            disciplinaAdapter.updateDisciplina(disciplina);
        }
    };

    @OnClick(R.id.fab_add_disciplina)
    void addDisciplina() {
        showDisciplinaDialog(new Disciplina(cronograma.getId()));
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
