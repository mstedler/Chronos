package com.espweb.chronos.domain.interactors.artefato.impl;

import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.artefato.UpdateArtefatoInteractor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;

public class UpdateArtefatoInteractorImpl extends AbstractInteractor implements UpdateArtefatoInteractor {

    private Callback callback;
    private ArtefatoRepository artefatoRepository;
    private Artefato artefato;

    public UpdateArtefatoInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, ArtefatoRepository artefatoRepository, Artefato artefato) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.artefatoRepository = artefatoRepository;
        this.artefato = artefato;
    }

    @Override
    public void run() {
        try {
            artefatoRepository.update(artefato);
            mainThread.post(() -> callback.onArtefatoUpdated());
        } catch (InvalidArtefatoException e) {
            //mainThread.post(() -> callback.invalidArtefatoError());
        }
    }
}
