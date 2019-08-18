package com.espweb.chronos.presentation.ui.dialogs;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.presenters.AssuntoDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.AssuntoDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssuntoDialog extends DialogFragment implements AssuntoDialogPresenter.View {

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

    public static AssuntoDialog newInstance(Assunto assunto) {
        AssuntoDialog assuntoDialog = new AssuntoDialog();
        Bundle args = new Bundle();
        args.putParcelable("assunto", Parcels.wrap(assunto));
        assuntoDialog.setArguments(args);
        return assuntoDialog;
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
        int title = assunto.isNew() ? R.string.new_topic : R.string.edit_topic;
        tvTitle.setText(getString(title));
    }

    private void initFromBundle() {
        Bundle arguments = getArguments();
        if(arguments != null) {
            assunto = Parcels.unwrap(arguments.getParcelable("assunto"));
        }
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
        if(assunto.isNew()) {
            createAssunto();
        } else {
            updateAssunto();
        }
    }

    private void updateAssunto() {
        presenter.updateAssunto(assunto);
    }

    private void createAssunto() {
        presenter.createAssunto(assunto);
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
