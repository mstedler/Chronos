package com.espweb.chronos.presentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.presenters.CronogramaPresenter;
import com.espweb.chronos.presentation.presenters.impl.CronogramaPresenterImpl;
import com.espweb.chronos.storage.CronogramaRepositoryImpl;
import com.espweb.chronos.storage.DisciplinaRepositoryImpl;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CronogramaActivity extends BaseActivity implements CronogramaPresenter.View {

    private static final String INTENT_EXTRA_PARAM_CRONOGRAMA_ID = "com.espweb.INTENT_PARAM_CRONOGRAMA_ID";

    private CronogramaPresenter cronogramaPresenter;

    private long cronogramaId;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getCallingIntent(Context context, long cronogramaId) {
        Intent callingIntent = new Intent(context, CronogramaActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CRONOGRAMA_ID, cronogramaId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);

        cronogramaId = getIntent().getLongExtra(INTENT_EXTRA_PARAM_CRONOGRAMA_ID, -1);

        if (cronogramaId == -1) {
            Toast.makeText(this, R.string.cronograma_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ButterKnife.bind(this);

        CronogramaRepository cronogramaRepository = new CronogramaRepositoryImpl(this);
        Repository<Disciplina> disciplinaRepository = new DisciplinaRepositoryImpl(this);
        this.cronogramaPresenter = new CronogramaPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository,
                disciplinaRepository);

        cronogramaPresenter.getCronograma(cronogramaId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cronogramaPresenter.resume();
    }

    @Override
    public void showDisciplinas(List<Disciplina> disciplinas) {

    }

    @Override
    public void showCronograma(Cronograma cronograma) {
        toolbar.setTitle(cronograma.getTitulo());
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
