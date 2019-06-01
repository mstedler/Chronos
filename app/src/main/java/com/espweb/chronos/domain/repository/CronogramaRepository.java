package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.exceptions.GetAllCronogramasException;
import com.espweb.chronos.domain.model.Cronograma;

import java.util.List;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface CronogramaRepository {

    boolean insert(Cronograma model);

    boolean update(Cronograma model);

    Cronograma get(Object id);

    List<Cronograma> getAllCronogramas() throws GetAllCronogramasException;

    boolean delete(Cronograma model);
}
