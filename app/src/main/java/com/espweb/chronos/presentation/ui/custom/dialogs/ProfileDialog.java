package com.espweb.chronos.presentation.ui.custom.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espweb.chronos.R;
import com.espweb.chronos.data.UserRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.ProfileDialogPresenter;
import com.espweb.chronos.presentation.presenters.impl.ProfileDialogPresenterImpl;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.espweb.chronos.presentation.utils.ViewUtils;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileDialog extends DialogFragment implements ProfileDialogPresenter.View {

    public interface ProfileDialogListener {
        void onUserUpdated(User user);
    }

    private ProfileDialogListener profileDialogListener;
    private ProfileDialogPresenter profileDialogPresenter;
    private User user;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.til_nome)
    TextInputLayout tilNome;

    public static ProfileDialog newInstance(User user) {
        ProfileDialog profileDialog = new ProfileDialog();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        profileDialog.setArguments(args);
        return profileDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileDialogPresenter = new ProfileDialogPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new UserRepositoryImpl(requireContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_profile, container);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        buildFromArguments();
        fillForm();
        setTitle();
    }

    private void buildFromArguments() {
        if (getArguments() != null)
            user = Parcels.unwrap(getArguments().getParcelable("user"));
    }

    private void setTitle() {
        tvTitle.setText(R.string.editar);
    }

    private void fillForm() {
       tilNome.getEditText().setText(user.getName());
    }

    @Override
    public void onResume() {
        ViewUtils.putDialogOnCenter(requireDialog());
        super.onResume();
    }

    @OnClick(R.id.btn_cancelar)
    void cancelarClicked() {
        dismiss();
    }

    @OnClick(R.id.btn_save)
    void saveClicked() {
        buildFromForm();
        if(validate())
            save();
    }

    private void save() {
        profileDialogPresenter.updateUser(user);
    }

    private void buildFromForm() {
        String nome = tilNome.getEditText().getText().toString();
        user.setName(nome);
    }

    private boolean validate() {
        clearErrors();
        if(!user.isNomeValid()) {
            tilNome.setError(getString(R.string.maior_que_0));
            return false;
        }
        return true;
    }

    private void clearErrors() {
        tilNome.setError(null);
    }

    @Override
    public void onUserUpdated() {
        profileDialogListener.onUserUpdated(user);
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

    public void setProfileDialogListener(ProfileDialogListener profileDialogListener) {
        this.profileDialogListener = profileDialogListener;
    }
}
