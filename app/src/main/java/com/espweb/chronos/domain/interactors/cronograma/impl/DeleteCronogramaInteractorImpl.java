package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.DeleteCronogramaInteractor;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;

public class DeleteCronogramaInteractorImpl extends AbstractInteractor implements DeleteCronogramaInteractor {

    private Repository<Cronograma> cronogramaRepository;
    private Callback callback;
    private long cronogramaId;

    public DeleteCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          Repository<Cronograma> cronogramaRepository,
                                          long cronogramaId) {
        super(threadExecutor, mainThread);

        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
        this.cronogramaId = cronogramaId;
    }

    @Override
    public void run() {
        Cronograma cronograma = new Cronograma();
        cronograma.setId(cronogramaId);
        cronogramaRepository.delete(cronograma);

        mainThread.post(() -> callback.onCronogramaDeleted());
    }
}
