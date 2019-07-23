package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.cronograma.CreateCronogramaInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;

public class CreateCronogramaInteractorImpl extends AbstractInteractor implements CreateCronogramaInteractor {

    private Callback callback;
    private Repository<Cronograma> cronogramaRepository;
    private Cronograma cronograma;
    long userId;

    public CreateCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Cronograma> cronogramaRepository,
                                          Cronograma cronograma,
                                          long userId) {
        super(threadExecutor, mainThread);
        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
        this.cronograma = cronograma;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            cronograma.setUserId(userId);
            long cronogramaId = cronogramaRepository.insert(cronograma);
            cronograma.setId(cronogramaId);
            mainThread.post(() -> callback.onCronogramaCreated(cronograma));

        } catch (Exception e) {
            mainThread.post(() -> callback.onError("Erro ao criar cronograma"));
        }
    }
}
