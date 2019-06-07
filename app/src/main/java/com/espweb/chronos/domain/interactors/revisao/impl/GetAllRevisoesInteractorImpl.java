package com.espweb.chronos.domain.interactors.revisao.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.revisao.GetAllRevisaoInteractor;

public class GetAllRevisaoInteractorImpl extends AbstractInteractor implements GetAllRevisaoInteractor {
    public GetAllRevisaoInteractorImpl(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
    }

    @Override
    public void run() {

    }
}
