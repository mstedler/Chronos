package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.model.Cronograma;

import java.util.List;

public interface CronogramaRepository extends Repository<Cronograma> {
    List<Cronograma> getAll(long userId, boolean fetchFromWeb);
    int getCronogramasCount(long userId);
}
