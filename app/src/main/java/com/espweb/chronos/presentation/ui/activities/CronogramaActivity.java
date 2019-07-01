package com.espweb.chronos.presentation.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.ui.adapters.data.DisciplinaAssuntoProvider;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.impl.CronogramaPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter;
import com.espweb.chronos.presentation.ui.fragments.AssuntoDialog;
import com.espweb.chronos.presentation.ui.fragments.DisciplinaDialog;
import com.espweb.chronos.storage.AssuntoRepositoryImpl;
import com.espweb.chronos.storage.CronogramaRepositoryImpl;
import com.espweb.chronos.storage.DisciplinaRepositoryImpl;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaActivity extends BaseActivity implements
        CronogramaPresenter.View,
        DisciplinaAdapter.AssuntoListListener,
        DisciplinaAdapter.DisciplinaListListener,
        AssuntoDialog.CreateAssuntoDialogListener,
        DisciplinaDialog.DisciplinaDialogListener {

    private static final String INTENT_EXTRA_PARAM_CRONOGRAMA_ID = "com.espweb.INTENT_PARAM_CRONOGRAMA_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_disciplinas)
    RecyclerView rv_disciplinas;
    @BindView(R.id.fab_add_disciplina)
    FloatingActionButton fabAddDisciplina;

    private CronogramaPresenter cronogramaPresenter;
    private long cronogramaId;
    private DisciplinaAdapter disciplinaAdapter;

    private int lastActionGroupPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);
        ButterKnife.bind(this);

        cronogramaId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_CRONOGRAMA_ID, -1);

        if (cronogramaId == -1) {
            Toast.makeText(this, R.string.cronograma_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        CronogramaRepository cronogramaRepository = new CronogramaRepositoryImpl(this);
        Repository<Disciplina> disciplinaRepository = new DisciplinaRepositoryImpl(this);
        Repository<Assunto> assuntoRepository = new AssuntoRepositoryImpl(this);

        this.cronogramaPresenter = new CronogramaPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository,
                disciplinaRepository,
                assuntoRepository);

        setUpRecyclerView();
        setUpToolbar();

        cronogramaPresenter.getCronograma(cronogramaId);
        cronogramaPresenter.getAllDisciplinas(cronogramaId);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showDisciplinas(List<Disciplina> disciplinas) {
        disciplinaAdapter.setDisciplinas(disciplinas);
    }

    @Override
    public void showCronograma(Cronograma cronograma) {
        toolbar.setTitle(cronograma.getTitulo());
    }

    @Override
    public void addAssuntoToList(Assunto assunto) {
        disciplinaAdapter.addAssunto(assunto, lastActionGroupPosition);
    }

    @Override
    public void onDisciplinaDeleted() {
        disciplinaAdapter.removeDisciplina(lastActionGroupPosition);
    }

    @Override
    public void addDisciplinaToList(Disciplina disciplina) {
        disciplinaAdapter.addDisciplina(disciplina);
    }

    @Override
    public void updateDisciplinaOnList(Disciplina disciplina) {
        disciplinaAdapter.updateDisciplina(disciplina, lastActionGroupPosition);
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

    public static Intent getCallingIntent(Context context, long cronogramaId) {
        Intent callingIntent = new Intent(context, CronogramaActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CRONOGRAMA_ID, cronogramaId);
        return callingIntent;
    }

    private void setUpRecyclerView() {
        RecyclerView.Adapter wrappedAdapter;
        RecyclerViewExpandableItemManager expandableItemManager = new RecyclerViewExpandableItemManager(null);
        RecyclerViewSwipeManager swipeManager = new RecyclerViewSwipeManager();
        disciplinaAdapter = new DisciplinaAdapter(this, expandableItemManager, new DisciplinaAssuntoProvider());
        disciplinaAdapter.setAssuntoListListener(this);
        disciplinaAdapter.setDisciplinaListListener(this);

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
        animator.setSupportsChangeAnimations(false);

        //é necessário fazer o wrap para cada manager
        wrappedAdapter = expandableItemManager.createWrappedAdapter(disciplinaAdapter);
        wrappedAdapter = swipeManager.createWrappedAdapter(wrappedAdapter);

        rv_disciplinas.setLayoutManager(new LinearLayoutManager(this));
        rv_disciplinas.setItemAnimator(animator);
        rv_disciplinas.setAdapter(wrappedAdapter);
        rv_disciplinas.setHasFixedSize(false);

        //Attach managers na RecyclerView;
        swipeManager.attachRecyclerView(rv_disciplinas);
        expandableItemManager.attachRecyclerView(rv_disciplinas);

        rv_disciplinas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    fabAddDisciplina.hide();
                } else {
                    fabAddDisciplina.show();
                }
            }
        });

    }

    @Override
    public void onAssuntoClicked(Assunto assunto) {
        navigator.navigateToAssunto(this, assunto.getId());
    }

    @Override
    public void onEditClicked(Disciplina disciplina, int lastActionGroupPosition) {
        this.lastActionGroupPosition = lastActionGroupPosition;
        DialogFragment editDisciplinaDialog = DisciplinaDialog.newInstance(cronogramaId, disciplina);
        editDisciplinaDialog.show(getSupportFragmentManager(), "EDIT_DISCIPLINA_DIALOG");
    }

    @Override
    public void onDeleteClicked(long id, int lastActionGroupPosition) {
        this.lastActionGroupPosition = lastActionGroupPosition;
        cronogramaPresenter.deleteDisciplina(id);
    }

    @Override
    public void onAddAssuntoClicked(Disciplina disciplina, int lastActionGroupPosition) {
        this.lastActionGroupPosition = lastActionGroupPosition;
        DialogFragment createAssuntoDialog = AssuntoDialog.newInstance(disciplina, null);
        createAssuntoDialog.show(getSupportFragmentManager(), "CREATE_ASSUNTO_DIALOG");
    }

    @Override
    public void createAssunto(long disciplinaId, String descricao, String anotacao) {
        cronogramaPresenter.createAssunto(disciplinaId, descricao, anotacao);
    }

    @OnClick(R.id.fab_add_disciplina)
    void addDisciplina(){
        DialogFragment createDisciplinaDialog = DisciplinaDialog.newInstance(cronogramaId, null);
        createDisciplinaDialog.show(getSupportFragmentManager(), "CREATE_DISCIPLINA_DIALOG");
    }

    @Override
    public void createDisciplina(long cronogramaId, String nome) {
        cronogramaPresenter.createDisciplina(cronogramaId, nome);
    }

    @Override
    public void updateDisciplina(long disciplinaId, String nome) {
        cronogramaPresenter.updateDisciplina(disciplinaId, nome);
    }


}
