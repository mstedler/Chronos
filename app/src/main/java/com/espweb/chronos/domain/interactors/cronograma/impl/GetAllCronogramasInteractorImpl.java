package com.espweb.chronos.domain.interactors.impl;

import com.espweb.chronos.domain.exceptions.GetAllCronogramasException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;

import java.util.List;

public class GetAllCronogramasInteractorImpl extends AbstractInteractor implements GetAllCronogramasInteractor {

    private GetAllCronogramasInteractor.Callback callback;
    private CronogramaRepository cronogramaRepository;

    public GetAllCronogramasInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           Callback callback, CronogramaRepository cronogramaRepository) {
        super(threadExecutor, mainThread);


        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
    }

    @Override
    public void run() {
        final List<Cronograma> cronogramas = cronogramaRepository.getAllCronogramas();
        if(cronogramas.size() == 0) {
            mainThread.post(() -> callback.onCronogramasRetrieved(cronogramas));
        } else {
            mainThread.post(() -> callback.onError("Nao existem cronogramas."));
        }
    }
}
