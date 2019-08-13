package com.espweb.chronos.presentation.ui.dialogs;

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
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.DisciplinaDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.DisciplinaDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisciplinaDialog extends DialogFragment implements DisciplinaDialogPresenter.View {

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

    private Disciplina disciplina;
    private DisciplinaDialogPresenter disciplinaDialogPresenter;

    private DisciplinaDialogListener disciplinaDialogListener;

    public DisciplinaDialog() {

    }

    public static DisciplinaDialog newInstance(Disciplina disciplina) {
        DisciplinaDialog disciplinaDialog = new DisciplinaDialog();
        Bundle args = new Bundle();
        args.putParcelable("disciplina", Parcels.wrap(disciplina));
        disciplinaDialog.setArguments(args);
        return disciplinaDialog;
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
        if(getArguments() != null)
            disciplina = Parcels.unwrap(getArguments().getParcelable("disciplina"));
    }

    private void fillForm() {
        tilNome.getEditText().setText(disciplina.getNome());
        tilDescricao.getEditText().setText(disciplina.getDescricao());
    }

    private void setTitle() {
        int title = disciplina.isNew() ? R.string.new_subject : R.string.edit;
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
        if(disciplina.isNew()) {
            createDisciplina();
        } else {
            updateDisciplina();
        }
    }

    private void updateDisciplina() {
        disciplinaDialogPresenter.updateDisciplina(disciplina);
    }

    private void createDisciplina() {
        disciplinaDialogPresenter.createDisciplina(disciplina);
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
