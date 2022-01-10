package edu.bbte.idde.jaim1826.spring.dao;

import edu.bbte.idde.jaim1826.spring.model.Component;

import java.util.Collection;

public interface ComponentDao extends Dao<Component> {
    Collection<Component> findByCategory(String category);
}
