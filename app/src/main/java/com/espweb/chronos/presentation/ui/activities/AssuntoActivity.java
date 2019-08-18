package com.espweb.chronos.presentation.ui.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.data.AssuntoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.presenters.AssuntoPresenter;
import com.espweb.chronos.presentation.presenters.impl.AssuntoPresenterImpl;
import com.espweb.chronos.data.ArtefatoRepositoryImpl;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.dialogs.AssuntoDialog;
import com.espweb.chronos.presentation.ui.dialogs.YesNoDialog;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.ui.dialogs.factory.ArtefatoDialogFactory;
import com.espweb.chronos.threading.MainThreadImpl;
import com.github.clans.fab.FloatingActionMenu;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssuntoActivity extends BaseActivity implements
        AssuntoPresenter.View,
        ArtefatoAdapter.ArtefatoListListener,
        ArtefatoDialog.ArtefatoDialogListener {

    private static final String INTENT_EXTRA_PARAM_ASSUNTO = "com.espweb.INTENT_PARAM_ASSUNTO";

    public static Intent getCallingIntent(Context context, Assunto assunto) {
        Intent callingIntent = new Intent(context, AssuntoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("assunto", Parcels.wrap(assunto));
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ASSUNTO, bundle);
        return callingIntent;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_artefatos)
    RecyclerView rvArtefatos;

    @BindView(R.id.fa_menu)
    FloatingActionMenu faMenu;

    private AssuntoPresenter assuntoPresenter;
    private ArtefatoAdapter artefatoAdapter;

    private Assunto assunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getBundleExtra(INTENT_EXTRA_PARAM_ASSUNTO);
        assunto = Parcels.unwrap(bundle.getParcelable("assunto"));

        if (assunto == null) {
            Toast.makeText(this, R.string.assunto_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        init();

        assuntoPresenter.getAllArtefatos(assunto.getId());
    }

    private void init() {
        initToolbar();
        initPresenter();
        initAdapter();
        initRecyclerView();
    }

    private void initToolbar() {
        updateToolbarTitle();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void updateToolbarTitle() {
        toolbar.setTitle(assunto.getDescricao());
    }

    private void initPresenter() {
        ArtefatoRepository artefatoRepository = new ArtefatoRepositoryImpl(this);
        AssuntoRepositoryImpl assuntoRepository = new AssuntoRepositoryImpl(this);
        assuntoPresenter = new AssuntoPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, artefatoRepository, assuntoRepository);
    }

    private void initAdapter() {
        artefatoAdapter = new ArtefatoAdapter();
        artefatoAdapter.setArtefatoListListener(this);
    }

    private void initRecyclerView() {
        rvArtefatos.setLayoutManager(new LinearLayoutManager(this));
        rvArtefatos.setAdapter(artefatoAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            showAssuntoDialog();
        } else if (item.getItemId() == R.id.item_delete) {
            showDeleteDialog();
        }
        return true;
    }

    YesNoDialog.YesNoDialogListener listener = new YesNoDialog.YesNoDialogListener() {
        @Override
        public void yesClicked() {
            assuntoPresenter.deleteAssunto(assunto);
        }
    };

    private void showDeleteDialog() {
        YesNoDialog confirmDelete = YesNoDialog.newInstance(getString(R.string.delete), getString(R.string.are_you_sure));
        confirmDelete.setListener(listener);
        confirmDelete.show(getSupportFragmentManager(), "YES_NO_DIALOG");
    }

    private AssuntoDialog.AssuntoDialogListener assuntoDialogListener = new AssuntoDialog.AssuntoDialogListener() {
        @Override
        public void onAssuntoCreated(Assunto assunto) {
            //////
        }

        @Override
        public void onAssuntoUpdated(Assunto assunto) {
            AssuntoActivity.this.assunto = assunto;
            updateToolbarTitle();
        }
    };

    private void showAssuntoDialog() {
        AssuntoDialog assuntoDialog = AssuntoDialog.newInstance(assunto);
        assuntoDialog.setAssuntoDialogListener(assuntoDialogListener);
        assuntoDialog.show(getSupportFragmentManager(), "ASSUNTO_DIALOG");
    }

    @Override
    public void showArtefatos(List<Artefato> artefatos) {
        artefatoAdapter.setArtefatos(artefatos);
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

    @Override
    public void onArtefatoCreated(Artefato artefato) {
        faMenu.close(false);
        artefatoAdapter.addArtefato(artefato);
    }

    @Override
    public void onArtefatoUpdated(Artefato artefato) {
        artefatoAdapter.updateArtefato(artefato);
    }

    @OnClick(R.id.fab_add_material)
    void onAddMaterialClick() {
        showDialogFor(new Material(assunto.getId()));
    }

    @OnClick(R.id.fab_add_exercicio)
    void onAddExercicioClick() {
        showDialogFor(new Exercicio(assunto.getId()));
    }

    @OnClick(R.id.fab_add_revisao)
    void onAddRevisaoClick() {
        showDialogFor(new Revisao(assunto.getId()));
    }

    private void showDialogFor(Artefato artefato) {
        ArtefatoDialog artefatoDialog = ArtefatoDialogFactory.createFor(artefato);
        artefatoDialog.show(getSupportFragmentManager(), "ARTEFATO_DIALOG");

//       new AlertDialog
//               .Builder(this)
//               .setView(R.layout.dialog_exercicio)
//               .show();

    }


    @Override
    public void onArtefatoClicked(Artefato artefato) {
        showDialogFor(artefato);
    }
}
