package com.espweb.chronos.domain.repository;

import com.espweb.chronos.domain.model.Cronograma;

import java.util.List;

public interface Repository<T> {
    long insert(long parentId, T model);
    boolean update(T model);
    boolean delete(long id);
    T get(long id);
    List<T> getAll(long parentId);
}
