package com.espweb.chronos.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.data.SessaoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.ui.adapters.CronogramaAdapter;
import com.espweb.chronos.presentation.ui.dialogs.CronogramaDialog;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.presentation.presenters.MainPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter.View;
import com.espweb.chronos.presentation.presenters.impl.MainPresenterImpl;
import com.espweb.chronos.presentation.ui.dialogs.YesNoDialog;
import com.espweb.chronos.threading.MainThreadImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private MainPresenter mainPresenter;
    private CronogramaAdapter cronogramaAdapter;

    @BindView(R.id.rv_cronogramas)
    RecyclerView rvCronogramas;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        mainPresenter.getUser();
    }

    private void init() {
        initToolbar();

        initPresenter();

        initAdapter();

        initRecyclerView();
    }
    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }


    private void initPresenter() {
        Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository = new CronogramaRepositoryImpl(this);
        SessaoRepository sessaoRepository = new SessaoRepositoryImpl(this);
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this,
                cronogramaRepository,
                sessaoRepository);
    }

    private void initAdapter() {
        cronogramaAdapter = new CronogramaAdapter(this);
    }

    CronogramaAdapter.CronogramaListListener cronogramaListListener = cronograma -> {
        if (cronograma != null) {
            navigator.navigateToCronograma(MainActivity.this, cronograma.getId());
        }
    };

    private void initRecyclerView() {
        cronogramaAdapter.setCronogramaListListener(cronogramaListListener);
        rvCronogramas.setLayoutManager(new LinearLayoutManager(this));
        rvCronogramas.setAdapter(cronogramaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            showYesNoDialog();
        }
        return true;
    }

    YesNoDialog.YesNoDialogListener yesNoDialogListener = new YesNoDialog.YesNoDialogListener() {
        @Override
        public void yesClicked() {
            mainPresenter.logout();
        }
    };

    public void showYesNoDialog() {
        YesNoDialog logoutDialog = YesNoDialog.newInstance(getString(R.string.logout), getString(R.string.are_you_sure));
        logoutDialog.setListener(yesNoDialogListener);
        logoutDialog.show(getSupportFragmentManager(), "YES_NO_DIALOG");

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
    }

    @Override
    public void navigateToLogin() {
        navigator.navigateToLogin(this);
        finish();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    CronogramaDialog.CronogramaDialogListener cronogramaDialogListener = new CronogramaDialog.CronogramaDialogListener() {
        @Override
        public void onCronogramaCreated(Cronograma cronograma) {
            new Handler().postDelayed(() -> cronogramaAdapter.addCronograma(cronograma), 500);
        }

        @Override
        public void onCronogramaUpdated(Cronograma cronograma) {}
    };

    @OnClick(R.id.fab_add_cronograma)
    public void addCronogramaClick() {
        CronogramaDialog cronogramaDialog = CronogramaDialog.newInstance(new Cronograma(user.getId()));
        cronogramaDialog.setListener(cronogramaDialogListener);
        cronogramaDialog.show(getSupportFragmentManager(), "CRONOGRAMA_ADD_DIALOG");
    }
}
