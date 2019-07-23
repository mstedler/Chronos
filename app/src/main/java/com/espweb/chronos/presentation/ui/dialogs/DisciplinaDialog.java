package com.espweb.chronos.presentation.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.data.DisciplinaRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.presenters.DisciplinaDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.DisciplinaDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisciplinaDialog extends DialogFragment implements DisciplinaDialogPresenter.View {

    private long cronogramaId;

    private Disciplina disciplina;
    private DisciplinaDialogPresenter disciplinaDialogPresenter;

    public interface DisciplinaDialogListener {
        void onDisciplinaCreated(Disciplina disciplina);
        void onDisciplinaUpdated(Disciplina disciplina);
    }

    @BindView(R.id.til_name)
    TextInputLayout tilNome;

    @BindView(R.id.til_descricao)
    TextInputLayout tilDescricao;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private DisciplinaDialogListener disciplinaDialogListener;

    public DisciplinaDialog() {

    }

    public static DisciplinaDialog newInstance(long cronogramaId, Disciplina disciplina) {
        DisciplinaDialog disciplinaDialog = new DisciplinaDialog();
        Bundle args = createBundleWith(cronogramaId, disciplina);
        disciplinaDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TitledDialog);
        disciplinaDialog.setArguments(args);
        return disciplinaDialog;
    }

    public static DisciplinaDialog newInstance(long cronogramaId) {
       return newInstance(cronogramaId, null);
    }

    private static Bundle createBundleWith(long cronogramaId, Disciplina disciplina) {
        Bundle args = new Bundle();
        args.putLong("idCronograma", cronogramaId);
        if (disciplina != null) {
            args.putLong("idDisciplina", disciplina.getId());
            args.putString("nomeDisciplina", disciplina.getNome());
            args.putString("descricaoDisciplina", disciplina.getDescricao());
        }
        return args;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_disciplina, container);
        ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
        initPresenter();

        buildFromArguments();

        fillForm();

        setTitle();
    }

    private void initPresenter() {
        disciplinaDialogPresenter = new DisciplinaDialogPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new DisciplinaRepositoryImpl(getActivity()));
    }

    private void buildFromArguments() {
        cronogramaId = getArguments().getLong("idCronograma", -1);
        long id = getArguments().getLong("idDisciplina", -1);
        String nome = getArguments().getString("nomeDisciplina", "");
        String descricao = getArguments().getString("descricaoDisciplina", "");

        disciplina = new Disciplina();
        disciplina.setId(id);
        disciplina.setNome(nome);
        disciplina.setDescricao(descricao);
    }

    private void fillForm() {
        tilNome.getEditText().setText(disciplina.getNome());
        tilDescricao.getEditText().setText(disciplina.getDescricao());
    }

    private void setTitle() {
        int title = disciplina.getId() == -1 ? R.string.new_subject : R.string.edit;
        tvTitle.setText(title);
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {
        buildDisciplina();
        if(disciplina.getId() == -1) {
            createDisciplina();
        } else {
            updateDisciplina();
        }
    }

    private void updateDisciplina() {
        disciplinaDialogPresenter.updateDisciplina(disciplina);
    }

    private void createDisciplina() {
        disciplinaDialogPresenter.createDisciplina(cronogramaId, disciplina);
    }

    private void buildDisciplina() {
        String nome = tilNome.getEditText().getText().toString().trim();
        String descricao = tilDescricao.getEditText().getText().toString().trim();
        disciplina.setNome(nome);
        disciplina.setDescricao(descricao);
    }

    @OnClick(R.id.btn_cancelar)
    public void cancel() {
        getDialog().dismiss();
    }

    @Override
    public void setNomeError(String message) {
        tilNome.setError(message);
    }

    @Override
    public void onDisciplinaCreated(Disciplina disciplina) {
        disciplinaDialogListener.onDisciplinaCreated(disciplina);
        dismiss();
    }

    @Override
    public void onDisciplinaUpdated() {
        disciplinaDialogListener.onDisciplinaUpdated(disciplina);
        dismiss();
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

    public void setListener(DisciplinaDialogListener disciplinaDialogListener) {
        this.disciplinaDialogListener = disciplinaDialogListener;
    }
}
