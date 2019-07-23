package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.cronograma.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateCronogramaInteractorImpl extends AbstractInteractor implements UpdateCronogramaInteractor {

    private Repository<Cronograma> cronogramaRepository;
    private Callback callback;
    private Cronograma cronograma;

    public UpdateCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Cronograma> cronogramaRepository,
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
        cronogramaRepository.update(cronograma);
        mainThread.post(() -> callback.onCronogramaUpdated());
    }
}
