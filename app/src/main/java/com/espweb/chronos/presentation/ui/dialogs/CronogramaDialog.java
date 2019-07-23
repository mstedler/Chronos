package com.espweb.chronos.presentation.ui.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.data.CronogramaRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.presentation.presenters.CronogramaDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.CronogramaDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CronogramaDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, CronogramaDialogPresenter.View {

    public interface CronogramaDialogListener {
        void onCronogramaCreated(Cronograma cronograma);
        void onCronogramaUpdated(Cronograma cronograma);
    }

    private CronogramaDialogListener cronogramaDialogListener;

    @BindView(R.id.til_titulo)
    TextInputLayout tilTitulo;

    @BindView(R.id.til_descricao)
    TextInputLayout tilDescricao;

    @BindView(R.id.til_data_inicial)
    TextInputLayout tilDataInicial;

    @BindView(R.id.til_data_final)
    TextInputLayout tilDataFinal;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private CronogramaDialogPresenter presenter;

    private Cronograma cronograma;
    private long userId;

    public CronogramaDialog() {

    }

    private static Bundle createBundleWith(long userId, Cronograma cronograma) {
        Bundle args = new Bundle();
        args.putLong("userId", userId);
        if(cronograma != null) {
            args.putLong("id", cronograma.getId());
            args.putString("titulo", cronograma.getTitulo());
            args.putString("descricao", cronograma.getDescricao());
            args.putString("inicio", DateUtils.formatDate(cronograma.getInicio()));
            args.putString("fim", DateUtils.formatDate(cronograma.getFim()));
            args.putString("uuid", cronograma.getUuid());
        }
        return args;
    }

    private static CronogramaDialog newInstance(long userId, Cronograma cronograma) {
        CronogramaDialog cronogramaDialog = new CronogramaDialog();
        Bundle args = createBundleWith(userId, cronograma);
        cronogramaDialog.setStyle(STYLE_NORMAL, R.style.TitledDialog);
        cronogramaDialog.setArguments(args);
        return cronogramaDialog;
    }

    public static CronogramaDialog newInstance(long userId) {
        return newInstance(userId, null);
    }

    public static CronogramaDialog newInstance(Cronograma cronograma) {
        return newInstance(-1, cronograma);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CronogramaDialogPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new CronogramaRepositoryImpl(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_cronograma, container);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        userId = getArguments().getLong("userId", -1);

        buildCronogramaFromBundle();

        fillForm();
        setTitle();
    }

    private void buildCronogramaFromBundle() {
        long id = getArguments().getLong("id", -1);
        String uuid = getArguments().getString("uuid", "");
        String titulo = getArguments().getString("titulo", "");
        String descricao = getArguments().getString("descricao", "");
        Date inicio = DateUtils.parse(getArguments().getString("inicio", ""));
        Date fim = DateUtils.parse(getArguments().getString("fim", ""));

        cronograma = new Cronograma(id, uuid, titulo, descricao, inicio, fim);
    }

    private void setTitle() {
        int title = cronograma.getId() == -1 ? R.string.new_cronograma : R.string.edit;
        tvTitle.setText(title);
    }

    private void fillForm() {
        tilTitulo.getEditText().setText(cronograma.getTitulo());
        tilDescricao.getEditText().setText(cronograma.getDescricao());
        tilDataInicial.getEditText().setText(DateUtils.formatDate(cronograma.getInicio()));
        tilDataFinal.getEditText().setText(DateUtils.formatDate(cronograma.getFim()));
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_cancelar)
    void cancelarClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_save)
    void saveClicked(){
        buildFromForm();
        if(cronograma.getId() != -1) {
            updateCronograma();
        } else {
            createCronograma();
        }
    }

    private void buildFromForm() {
        String titulo = tilTitulo.getEditText().getText().toString();
        String descricao = tilDescricao.getEditText().getText().toString();
        String inicio = tilDataInicial.getEditText().getText().toString();
        String fim = tilDataFinal.getEditText().getText().toString();

        cronograma.setTitulo(titulo);
        cronograma.setDescricao(descricao);
        cronograma.setInicio(DateUtils.parse(inicio));
        cronograma.setFim(DateUtils.parse(fim));
    }

    private void createCronograma() {
        presenter.createCronograma(userId, cronograma);
    }

    @Override
    public void cronogramaCreated(Cronograma cronograma) {
        cronogramaDialogListener.onCronogramaCreated(cronograma);
    }

    private void updateCronograma() {
        presenter.updateCronograma(cronograma);
    }

    @Override
    public void cronogramaUpdated() {
        cronogramaDialogListener.onCronogramaUpdated(cronograma);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @OnClick({R.id.til_data_inicial, R.id.til_data_final})
    public void onClick(View v) {

    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void setTitleError(String errorMessage) {
        tilTitulo.setError(errorMessage);
    }

    @Override
    public void setDescriptionError(String errorMessage) {
        tilDescricao.setError(errorMessage);
    }

    @Override
    public void setInitialDateError(String errorMessage) {
        tilDataInicial.setError(errorMessage);
    }

    @Override
    public void setFinalDateError(String errorMessage) {
        tilDataFinal.setError(errorMessage);
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

    public void setListener(CronogramaDialogListener cronogramaDialogListener) {
        this.cronogramaDialogListener = cronogramaDialogListener;
    }
}
