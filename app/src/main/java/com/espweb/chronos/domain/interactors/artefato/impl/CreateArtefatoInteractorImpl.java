package com.espweb.chronos.domain.interactors.artefato.impl;

import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.artefato.CreateArtefatoInteractor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.ArtefatoRepository;

public class CreateArtefatoInteractorImpl extends AbstractInteractor implements CreateArtefatoInteractor {

    private CreateArtefatoInteractor.Callback callback;
    private ArtefatoRepository artefatoRepository;
    private Artefato artefato;
    private long assuntoId;

    public CreateArtefatoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        ArtefatoRepository artefatoRepository,
                                        Artefato artefato,
                                        long assuntoId) {
        super(threadExecutor, mainThread);

        if (artefatoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }
        this.callback = callback;
        this.artefatoRepository = artefatoRepository;
        this.artefato = artefato;
        this.assuntoId = assuntoId;
    }

    @Override
    public void run() {
        try {
            artefato.setIdAssunto(assuntoId);
            long id = artefatoRepository.insert(artefato);
            artefato.setId(id);
            mainThread.post(() -> callback.onArtefatoCreated(artefato));
        } catch (InvalidArtefatoException e) {
            mainThread.post(() -> callback.onError("Erro ao criar artefato"));
        }
    }
}
