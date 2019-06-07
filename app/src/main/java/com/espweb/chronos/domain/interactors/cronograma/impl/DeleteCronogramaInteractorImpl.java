package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.DeleteCronogramaInteractor;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.repository.CronogramaRepository;

public class DeleteCronogramaInteractorImpl extends AbstractInteractor implements DeleteCronogramaInteractor {

    private CronogramaRepository cronogramaRepository;
    private Callback callback;
    private long cronogramaId;

    public DeleteCronogramaInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                          Callback callback,
                                          CronogramaRepository cronogramaRepository,
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
        boolean success = cronogramaRepository.delete(cronogramaId);

        if(success) {
            mainThread.post(() -> callback.onCronogramaDeleted());
        } else {
            mainThread.post(() -> callback.onError("Erro ao atualizar cronograma"));
        }
    }
}
