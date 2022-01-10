package edu.bbte.idde.jaim1826.backend.dao;

import edu.bbte.idde.jaim1826.backend.model.Component;

import java.util.Collection;

public interface ComponentDao extends Dao<Component> {
    Collection<Component> findAllByCategory(String category);
}
