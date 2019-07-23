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
import com.espweb.chronos.data.AssuntoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.presenters.AssuntoDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.AssuntoDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssuntoDialog extends DialogFragment implements AssuntoDialogPresenter.View {

    private Disciplina disciplina;
    private Assunto assunto;

    private AssuntoDialogPresenter presenter;

    public interface AssuntoDialogListener {
        void onAssuntoCreated(Assunto assunto);
        void onAssuntoUpdated(Assunto assunto);
    }

    private AssuntoDialogListener assuntoDialogListener;

    @BindView(R.id.til_descricao)
    TextInputLayout tilDescricao;


    @BindView(R.id.tv_title)
    TextView tvTitle;

    public AssuntoDialog() {

    }

    private static Bundle createBundleWith(Disciplina disciplina, Assunto assunto) {
        Bundle args = new Bundle();
        args.putLong("idDisciplina", disciplina.getId());
        args.putString("nomeDisciplina", disciplina.getNome());

        if(assunto != null) {
            args.putLong("idAssunto", assunto.getId());
            args.putString("descricaoAssunto", assunto.getDescricao());
        }
        return args;
    }

    public static AssuntoDialog newInstance(Disciplina disciplina, Assunto assunto) {
        AssuntoDialog assuntoDialog = new AssuntoDialog();
        Bundle args = createBundleWith(disciplina, assunto);
        assuntoDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TitledDialog);
        assuntoDialog.setArguments(args);
        return assuntoDialog;
    }

    public static AssuntoDialog newInstance(Disciplina disciplina) {
        return newInstance(disciplina, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AssuntoDialogPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new AssuntoRepositoryImpl(getActivity())
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_assunto, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        initFromBundle();

        buildForm();

        setTitle();
    }

    private void setTitle() {
        int title = assunto.getId() == -1 ? R.string.new_topic : R.string.edit_topic;
        tvTitle.setText(getString(title));
    }

    private void initFromBundle() {
        long idDisciplina = getArguments().getLong("idDisciplina", -1);
        String nomeDisciplina = getArguments().getString("nomeDisciplina", "");

        long idAssunto = getArguments().getLong("idAssunto", -1);
        String descricaoAssunto = getArguments().getString("descricaoAssunto", "");

        disciplina = new Disciplina();
        disciplina.setId(idDisciplina);
        disciplina.setNome(nomeDisciplina);

        assunto = new Assunto();
        assunto.setId(idAssunto);
        assunto.setDescricao(descricaoAssunto);
    }

    private void buildForm() {
        tilDescricao.getEditText().setText(assunto.getDescricao());
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(getDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_save)
    public void onSaveClick() {
        buildAssunto();
        if(assunto.getId() == -1) {
            createAssunto();
        } else {
            updateAssunto();
        }
    }

    private void updateAssunto() {
        presenter.updateAssunto(assunto);
    }

    private void createAssunto() {
        presenter.createAssunto(disciplina.getId(), assunto);
    }

    private void buildAssunto() {
        String descricao = tilDescricao.getEditText().getText().toString().trim();
        assunto.setDescricao(descricao);
    }

    @OnClick(R.id.btn_cancelar)
    public void cancel() {
        dismiss();
    }

    @Override
    public void onAssuntoCreated(Assunto assunto) {
        assuntoDialogListener.onAssuntoCreated(assunto);
        dismiss();
    }

    @Override
    public void onAssuntoUpdated() {
        assuntoDialogListener.onAssuntoUpdated(assunto);
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

    public void setAssuntoDialogListener(AssuntoDialogListener assuntoDialogListener) {
        this.assuntoDialogListener = assuntoDialogListener;
    }
}
