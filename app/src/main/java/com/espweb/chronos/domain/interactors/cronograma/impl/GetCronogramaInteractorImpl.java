package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;

public class GetCronogramaInteractorImpl extends AbstractInteractor implements GetCronogramaInteractor {

    private Repository<Cronograma> cronogramaRepository;
    private Callback callback;
    private long cronogramaId;

    public GetCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback,
                                       Repository<Cronograma> cronogramaRepository,
                                       long cronogramaId) {
        super(threadExecutor, mainThread);

        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.cronogramaRepository = cronogramaRepository;
        this.callback = callback;
        this.cronogramaId = cronogramaId;
    }

    @Override
    public void run() {
        Cronograma cronograma = cronogramaRepository.get(cronogramaId);

        if(cronograma != null) {
            mainThread.post(() -> callback.onCronogramaRetrieved(cronograma));
        } else {
            mainThread.post(() -> callback.cronogramaNotFound());
        }

    }
}
