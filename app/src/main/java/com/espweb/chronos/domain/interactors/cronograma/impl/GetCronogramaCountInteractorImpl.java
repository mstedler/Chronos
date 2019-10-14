package com.espweb.chronos.domain.interactors.cronograma.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.cronograma.GetCronogramaCountInteractor;
import com.espweb.chronos.domain.repository.CronogramaRepository;

public class GetCronogramaCountInteractorImpl extends AbstractInteractor implements GetCronogramaCountInteractor {

    private Callback callback;
    private CronogramaRepository cronogramaRepository;
    private long userId;

    public GetCronogramaCountInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, CronogramaRepository cronogramaRepository, long userId) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.cronogramaRepository = cronogramaRepository;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            int count = cronogramaRepository.getCronogramasCount(userId);
            mainThread.post(() -> callback.onCounted(count));
        } catch (Exception e) {
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
