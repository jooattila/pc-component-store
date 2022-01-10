package edu.bbte.idde.jaim1826.backend.dao;

import java.util.Collection;

public interface Dao<T> {
    void create(T entity);

    Collection<T> findAll();

    T findById(Long id);

    void update(T entity);

    void delete(Long id);
}
