package edu.bbte.idde.jaim1826.spring.dao;

import edu.bbte.idde.jaim1826.spring.model.BaseEntity;

import java.sql.SQLException;
import java.util.Collection;

public interface Dao<T extends BaseEntity> {

    T save(T object) throws SQLException;

    Collection<T> findAll() throws SQLException;

    T getById(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;
}
