package com.espweb.chronos.presentation.presenters.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetAllCronogramasInteractorImpl;
import com.espweb.chronos.domain.interactors.session.GetActiveUserInteractor;
import com.espweb.chronos.domain.interactors.session.SignOutInteractor;
import com.espweb.chronos.domain.interactors.session.impl.GetActiveUserInteractorImpl;
import com.espweb.chronos.domain.interactors.session.impl.SignOutInteractorImpl;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.domain.repository.SessaoRepository;
import com.espweb.chronos.presentation.converters.DomainToPresentationConverter;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.presenters.base.AbstractPresenter;
import com.espweb.chronos.presentation.presenters.MainPresenter;

import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        GetAllCronogramasInteractor.Callback,
        SignOutInteractor.Callback,
        GetActiveUserInteractor.Callback {

    private MainPresenter.View view;
    private Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository;
    private SessaoRepository sessaoRepository;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view,
                             Repository<com.espweb.chronos.domain.model.Cronograma> cronogramaRepository,
                             SessaoRepository sessaoRepository) {
        super(executor, mainThread);

        this.sessaoRepository = sessaoRepository;
        this.cronogramaRepository = cronogramaRepository;
        this.view = view;
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
    public void onUserSignOut() {
        view.navigateToLogin();
        view.showError("Usuário descontectado.");
    }

    @Override
    public void onUserRetrieved(com.espweb.chronos.domain.model.User user) {
        User pUser = DomainToPresentationConverter.convert(user);
        view.setUser(pUser);
        getAllCronogramas(user.getId());
    }

    @Override
    public void onUserNotFound() {
        view.navigateToLogin();
        view.showError("Algo deu errado, tente logar novamente.");
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onCronogramasRetrieved(List<com.espweb.chronos.domain.model.Cronograma> cronogramas) {
        List<Cronograma> pCronogramas = DomainToPresentationConverter.convert(cronogramas);
        view.showCronogramas(pCronogramas);
    }

    @Override
    public void onCronogramasNotFound() {

    }


    @Override
    public void getUser() {
        GetActiveUserInteractor getActiveUserInteractor = new GetActiveUserInteractorImpl(executor,
                mainThread,
                this,
                sessaoRepository);
        getActiveUserInteractor.execute();
    }

    @Override
    public void logout() {
        SignOutInteractor signOutInteractor = new SignOutInteractorImpl(executor, mainThread, this, sessaoRepository);
        signOutInteractor.execute();
    }

    @Override
    public void getAllCronogramas(long userId) {
        GetAllCronogramasInteractor getAllCronogramasInteractor =
                new GetAllCronogramasInteractorImpl(executor, mainThread, this, cronogramaRepository, userId);

        getAllCronogramasInteractor.execute();
    }
}
