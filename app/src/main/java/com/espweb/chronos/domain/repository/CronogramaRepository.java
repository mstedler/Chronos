package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.model.Cronograma;

import java.util.List;

public interface CronogramaRepository extends Repository<Cronograma> {
    long insert(Cronograma cronograma);
    List<Cronograma> getUnsynced();
    List<Cronograma> getAll();
}
