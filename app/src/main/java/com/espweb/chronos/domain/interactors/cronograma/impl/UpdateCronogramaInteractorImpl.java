package com.espweb.chronos.domain.interactors.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;

public class UpdateCronogramaInteractorImpl extends AbstractInteractor implements UpdateCronogramaInteractor {

    private CronogramaRepository cronogramaRepository;
    private Callback callback;
    private Cronograma cronograma;

    public UpdateCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
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
        boolean success = cronogramaRepository.update(cronograma);

        if(success) {
            mainThread.post(() -> callback.onCronogramaUpdated());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar cronograma."));
        }
    }
}