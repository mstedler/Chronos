package com.espweb.chronos.presentation.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.impl.CronogramaPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter;
import com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider;
import com.espweb.chronos.presentation.ui.dialogs.AssuntoDialog;
import com.espweb.chronos.presentation.ui.dialogs.CronogramaDialog;
import com.espweb.chronos.presentation.ui.dialogs.DisciplinaDialog;
import com.espweb.chronos.data.AssuntoRepositoryImpl;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.data.DisciplinaRepositoryImpl;
import com.espweb.chronos.presentation.ui.dialogs.YesNoDialog;
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

public class CronogramaActivity extends BaseActivity implements CronogramaPresenter.View {

    public static Intent getCallingIntent(Context context, long cronogramaId) {
        Intent callingIntent = new Intent(context, CronogramaActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CRONOGRAMA_ID, cronogramaId);
        return callingIntent;
    }

    private static final String INTENT_EXTRA_PARAM_CRONOGRAMA_ID = "com.espweb.INTENT_PARAM_CRONOGRAMA_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_disciplinas)
    RecyclerView rvDisciplinas;
    @BindView(R.id.fab_add_disciplina)
    FloatingActionButton fabAddDisciplina;

    private CronogramaPresenter cronogramaPresenter;
    private long cronogramaId;
    private DisciplinaAdapter disciplinaAdapter;

    private Cronograma cronograma;

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

        init();

        cronogramaPresenter.getCronograma(cronogramaId);
    }

    private void init() {
        initPresenter();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initPresenter() {
        Repository<Cronograma> cronogramaRepository = new CronogramaRepositoryImpl(this);
        Repository<Disciplina> disciplinaRepository = new DisciplinaRepositoryImpl(this);
        Repository<Assunto> assuntoRepository = new AssuntoRepositoryImpl(this);

        cronogramaPresenter = new CronogramaPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository,
                disciplinaRepository,
                assuntoRepository);
    }

    DisciplinaAdapter.DisciplinaListListener disciplinaListListener = new DisciplinaAdapter.DisciplinaListListener() {
        @Override
        public void onEditDisciplinaClicked(Disciplina disciplina) {
            showDisciplinaDialog(disciplina);
        }

        @Override
        public void onDeleteDisciplinaClicked(long id) {
            cronogramaPresenter.deleteDisciplina(id);
        }

        @Override
        public void onCreateAssuntoClicked(Disciplina disciplina) {
            showAssuntoDialog(disciplina, null);
        }
    };


    DisciplinaAdapter.AssuntoListListener assuntoListListener = assunto -> {
        navigator.navigateToAssunto(this, assunto.getId());
    };

    private void initRecyclerView() {
        RecyclerViewExpandableItemManager expandableItemManager = new RecyclerViewExpandableItemManager(null);

        disciplinaAdapter = new DisciplinaAdapter(this, expandableItemManager, new DisciplinaProvider());
        disciplinaAdapter.setDisciplinaListListener(disciplinaListListener);
        disciplinaAdapter.setAssuntoListListener(assuntoListListener);


        RecyclerView.Adapter wrappedAdapter = expandableItemManager.createWrappedAdapter(disciplinaAdapter);
        RecyclerViewSwipeManager swipeManager = new RecyclerViewSwipeManager();
        wrappedAdapter = swipeManager.createWrappedAdapter(wrappedAdapter);

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
        animator.setSupportsChangeAnimations(false);


        rvDisciplinas.setLayoutManager(new LinearLayoutManager(this));
        rvDisciplinas.setItemAnimator(animator);
        rvDisciplinas.setAdapter(wrappedAdapter);
        rvDisciplinas.setHasFixedSize(false);

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

    @Override
    protected void onResume() {
        super.onResume();
        cronogramaPresenter.getAllDisciplinas(cronogramaId);
    }

    @Override
    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    @Override
    public void bindCronogramaToView() {
        toolbar.setTitle(cronograma.getTitulo());
    }

    @Override
    public void showDisciplinas(List<Disciplina> disciplinas) {
        disciplinaAdapter.setDisciplinas(disciplinas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cronograma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            showCronogramaDialog();
        } else if (item.getItemId() == R.id.item_delete) {
            showYesNoDialog();
        }
        return true;
    }



    @Override
    public void onCronogramaDeleted() {
        finish();
    }

    CronogramaDialog.CronogramaDialogListener cronogramaDialogListener = new CronogramaDialog.CronogramaDialogListener() {
        @Override
        public void onCronogramaCreated(Cronograma cronograma) {

        }

        @Override
        public void onCronogramaUpdated(Cronograma cronograma) {
            setCronograma(cronograma);
            bindCronogramaToView();
        }
    };

    private void showCronogramaDialog() {
        CronogramaDialog cronogramaDialog = CronogramaDialog.newInstance(cronograma);
        cronogramaDialog.setListener(cronogramaDialogListener);
        cronogramaDialog.show(getSupportFragmentManager(), "CRONOGRAMA_DIALOG");
    }

    AssuntoDialog.AssuntoDialogListener assuntoDialogListener = new AssuntoDialog.AssuntoDialogListener() {
        @Override
        public void onAssuntoCreated(Assunto assunto) {
            disciplinaAdapter.addAssunto(assunto);
        }

        @Override
        public void onAssuntoUpdated(Assunto assunto) {

        }
    };

    private void showAssuntoDialog(Disciplina disciplina, Assunto assunto) {
        AssuntoDialog assuntoDialog = AssuntoDialog.newInstance(disciplina, assunto);
        assuntoDialog.setAssuntoDialogListener(assuntoDialogListener);
        assuntoDialog.show(getSupportFragmentManager(), "ASSUNTO_DIALOG");
    }

    YesNoDialog.YesNoDialogListener yesNoDialogListener = new YesNoDialog.YesNoDialogListener() {
        @Override
        public void yesClicked() {
            cronogramaPresenter.deleteCronograma(cronogramaId);
        }
    };

    private void showYesNoDialog() {
        YesNoDialog yesNoDialog = YesNoDialog.newInstance();
        yesNoDialog.setListener(yesNoDialogListener);
        yesNoDialog.show(getSupportFragmentManager(), "YES_NO_DIALOG");
    }


    @Override
    public void onDisciplinaDeleted() {
        disciplinaAdapter.removeDisciplina();
    }

    private void showDisciplinaDialog(Disciplina disciplina) {
        DisciplinaDialog disciplinaDialog = DisciplinaDialog.newInstance(cronogramaId, disciplina);
        disciplinaDialog.setListener(disciplinaDialogListener);
        disciplinaDialog.show(getSupportFragmentManager(), "DISCIPLINA_DIALOG");
    }

    DisciplinaDialog.DisciplinaDialogListener disciplinaDialogListener = new DisciplinaDialog.DisciplinaDialogListener() {
        @Override
        public void onDisciplinaCreated(Disciplina disciplina) {
            disciplinaAdapter.addDisciplina(disciplina);
        }

        @Override
        public void onDisciplinaUpdated(Disciplina disciplina) {
            disciplinaAdapter.updateDisciplina(disciplina);
        }
    };

    @OnClick(R.id.fab_add_disciplina)
    void addDisciplina() {
       showDisciplinaDialog(null);
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
