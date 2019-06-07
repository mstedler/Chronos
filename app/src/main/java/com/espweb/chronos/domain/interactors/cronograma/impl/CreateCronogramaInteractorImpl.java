package com.espweb.chronos.domain.interactors.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.CreateCronogramaInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;

import java.util.Date;

public class CreateCronogramaInteractorImpl extends AbstractInteractor implements CreateCronogramaInteractor {

    private CreateCronogramaInteractor.Callback callback;
    private CronogramaRepository cronogramaRepository;
    private Cronograma cronograma;

    public CreateCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          CronogramaRepository cronogramaRepository,
                                          Cronograma cronograma) {
        super(threadExecutor, mainThread);

        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
        this.cronograma = cronograma;
    }

    @Override
    public void run() {
        boolean success = cronogramaRepository.insert(cronograma);

        if (success) {
            mainThread.post(() -> callback.onCronogramaCreated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar cronograma"));
        }
    }
}
