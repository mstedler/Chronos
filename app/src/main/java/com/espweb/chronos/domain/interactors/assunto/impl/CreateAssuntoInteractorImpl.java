package com.espweb.chronos.domain.interactors.assunto.impl;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.interactors.assunto.CreateAssuntoInteractor;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.repository.Repository;

public class CreateAssuntoInteractorImpl extends AbstractInteractor implements CreateAssuntoInteractor {

    private Repository<Assunto> assuntoRepository;
    private Callback callback;
    private long disciplinaId;
    private String descricao;
    private String anotacao;

    public CreateAssuntoInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback,
                                       Repository<Assunto> assuntoRepository,
                                       long disciplinaId,
                                       String descricao, String anotacao) {
        super(threadExecutor, mainThread);

        if (assuntoRepository == null || callback == null) {
            throw new IllegalArgumentException("Argumentos nao podem ser nulos!");
        }

        this.callback = callback;
        this.assuntoRepository = assuntoRepository;
        this.disciplinaId = disciplinaId;
        this.descricao = descricao;
        this.anotacao = anotacao;
    }

    @Override
    public void run() {
        Assunto assunto = new Assunto();
        assunto.setDescricao(descricao);
        assunto.setAnotacao(anotacao);

        long assuntoId = assuntoRepository.insert(disciplinaId, assunto);

        if(assuntoId != 0) {
            assunto.setId(assuntoId);
            mainThread.post(() -> callback.onAssuntoCreated(assunto));
        } else {
            mainThread.post(() -> callback.onError("Erro ao criar assunto."));
        }
    }
}
