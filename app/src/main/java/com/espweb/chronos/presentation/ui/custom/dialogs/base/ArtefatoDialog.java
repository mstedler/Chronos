package com.espweb.chronos.presentation.ui.dialogs.base;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.data.ArtefatoRepositoryImpl;
import com.espweb.chronos.data.factory.ArtefatoRepositoryFactory;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.presenters.ArtefatoDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.ArtefatoDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class ArtefatoDialog<T extends Artefato> extends DialogFragment implements
        ArtefatoDialogPresenter.View {

    protected T artefato;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.til_descricao)
    protected TextInputLayout tilDescricao;

    @BindView(R.id.til_data)
    protected TextInputLayout tilData;

    private ArtefatoDialogPresenter artefatoDialogPresenter;

    public interface ArtefatoDialogListener {
        void onArtefatoCreated(Artefato artefato);
        void onArtefatoUpdated(Artefato artefato);
    }

    private ArtefatoDialogListener artefatoDialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public void onStart() {
        initView();
        ViewUtils.putDialogOnCenter(requireDialog());
        super.onStart();
    }

    private void initView() {
        buildFromArguments();
        fillForm();
        setTitle();
    }

    public abstract void buildFromArguments();

    public abstract void fillForm();

    public abstract void setTitle();

    @OnClick(R.id.btn_save)
    void onSaveClick(){
        buildFromForm();
        if(validate())
            save();
    }

    protected abstract boolean validate();

    private void save() {
        if(artefato.isNew()) {
            artefatoDialogPresenter.createArtefato(artefato.getIdAssunto(), artefato);
        } else {
            artefatoDialogPresenter.updateArtefato(artefato);
        }
    }

    public abstract void buildFromForm();

    private void initPresenter() {
        artefatoDialogPresenter = new ArtefatoDialogPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new ArtefatoRepositoryImpl(requireContext())
        );
    }

    @Override
    public void onArtefatoCreated(Artefato artefato) {
        artefatoDialogListener.onArtefatoCreated(artefato);
        dismiss();
    }

    @Override
    public void onArtefatoUpdated() {
        artefatoDialogListener.onArtefatoUpdated(artefato);
        dismiss();
    }

    @OnClick(R.id.btn_cancelar)
    void onCancelarClick() {
        dismiss();
    }

    public void setListener(ArtefatoDialogListener artefatoDialogListener) {
        this.artefatoDialogListener = artefatoDialogListener;
    }
}
