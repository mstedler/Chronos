package com.espweb.chronos.presentation.ui.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.AssuntoPresenter;
import com.espweb.chronos.presentation.presenters.impl.AssuntoPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.data.ArtefatoRepositoryImpl;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssuntoActivity extends BaseActivity implements AssuntoPresenter.View, ArtefatoAdapter.ArtefatoListListener {

    private static final String INTENT_EXTRA_PARAM_ASSUNTO_ID = "com.espweb.INTENT_PARAM_ASSUNTO_ID";

    private long assuntoId;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_artefatos)
    RecyclerView rvArtefatos;

    private ArtefatoAdapter artefatoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assunto);
        ButterKnife.bind(this);
        assuntoId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_ASSUNTO_ID, -1);

        if(assuntoId == -1) {
            Toast.makeText(this, R.string.assunto_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ArtefatoRepository artefatoRepository = new ArtefatoRepositoryImpl(this);
        AssuntoPresenter assuntoPresenter = new AssuntoPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, artefatoRepository);


        artefatoAdapter = new ArtefatoAdapter(this);

        setUpRecyclerView();

        assuntoPresenter.getAllArtefatos(assuntoId);

    }

    private void setUpRecyclerView() {
        artefatoAdapter.setArtefatoListListener(this);
        rvArtefatos.setLayoutManager(new LinearLayoutManager(this));
        rvArtefatos.setAdapter(artefatoAdapter);
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
    public void onArtefatoClicked(Artefato artefato) {

    }

    public static Intent getCallingIntent(Context context, long assuntoId) {
        Intent callingIntent = new Intent(context, AssuntoActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ASSUNTO_ID, assuntoId);
        return callingIntent;
    }
}
