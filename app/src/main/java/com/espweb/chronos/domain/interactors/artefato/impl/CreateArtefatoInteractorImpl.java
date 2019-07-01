package com.espweb.chronos.domain.interactors.artefato.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.artefato.CreateArtefatoInteractor;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.repository.Repository;

public class CreateArtefatoInteractorImpl extends AbstractInteractor implements CreateArtefatoInteractor {

    private CreateArtefatoInteractor.Callback callback;
    private Repository<Artefato> artefatoRepository;
    private Artefato artefato;
    private long assuntoId;

    public CreateArtefatoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback,
                                        Repository<Artefato> artefatoRepository,
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
        long id = artefatoRepository.insert(assuntoId, artefato);

        if(id != 0) {
            artefato.setId(id);
            mainThread.post(() -> callback.onArtefatoCreated(artefato));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar artefato"));
        }
    }
}
