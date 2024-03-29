package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;

import java.util.List;

public class GetAllCronogramasInteractorImpl extends AbstractInteractor implements GetAllCronogramasInteractor {

    private Callback callback;
    private CronogramaRepository cronogramaRepository;

    private long userId;
    private boolean fromWeb;
    public GetAllCronogramasInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           Callback callback,
                                           CronogramaRepository cronogramaRepository,
                                           long userId, boolean fromWeb) {
        super(threadExecutor, mainThread);

        if (cronogramaRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
        this.userId = userId;
        this.fromWeb = fromWeb;
    }

    @Override
    public void run() {
        try {
            final List<Cronograma> cronogramas = cronogramaRepository.getAll(userId, fromWeb);
            if (cronogramas.size() > 0) {
                mainThread.post(() -> callback.onCronogramasRetrieved(cronogramas));
            } else {
                mainThread.post(() -> callback.onCronogramasNotFound());
            }
        } catch (Exception e) {
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
