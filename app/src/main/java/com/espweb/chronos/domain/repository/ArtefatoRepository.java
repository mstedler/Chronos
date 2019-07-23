package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.model.Artefato;

import java.util.List;

public interface ArtefatoRepository<T extends Artefato> {
    long insert(T model) throws InvalidArtefatoException;
    void update(T model) throws InvalidArtefatoException;
    void delete(T model) throws InvalidArtefatoException;
    List<T> getAll(long parentId);
}
