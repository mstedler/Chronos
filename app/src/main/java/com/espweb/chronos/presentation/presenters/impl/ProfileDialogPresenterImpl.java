package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.user.UpdateUserInteractor;
import com.espweb.chronos.domain.interactors.user.UpdateUserInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.presentation.converters.PresentationToDomainConverter;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.ProfileDialogPresenter;

public class ProfileDialogPresenterImpl extends AbstractPresenter implements ProfileDialogPresenter, UpdateUserInteractor.Callback {

    private View view;
    private Repository<com.espweb.chronos.domain.model.User> userRepository;


    public ProfileDialogPresenterImpl(Executor executor, MainThread mainThread, View view, Repository<com.espweb.chronos.domain.model.User> userRepository) {
        super(executor, mainThread);
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void updateUser(User user) {
        UpdateUserInteractor updateUserInteractor = new UpdateUserInteractorImpl(executor, mainThread, this, userRepository, PresentationToDomainConverter.convert(user));
        updateUserInteractor.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onUserUpdated() {
        view.onUserUpdated();
    }

    @Override
    public void onError(String message) {

    }
}
