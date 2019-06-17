package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Assunto;

import java.util.List;

public interface AssuntoRepository extends Repository<Assunto> {
    List<Artefato> getAllArtefatos(long assuntoId);
}
