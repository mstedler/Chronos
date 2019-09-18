package com.espweb.chronos.domain.repository;

import java.util.List;

public interface Repository<T> {
    long insert(T model);
    void update(T model);
    void delete(long id);
    T get(long id);
    List<T> getAll(long parentId);
}
