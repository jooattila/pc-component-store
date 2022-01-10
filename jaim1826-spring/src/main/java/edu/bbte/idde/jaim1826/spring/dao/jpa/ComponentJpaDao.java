package edu.bbte.idde.jaim1826.spring.dao.jpa;

import edu.bbte.idde.jaim1826.spring.dao.ComponentDao;
import edu.bbte.idde.jaim1826.spring.model.Component;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("jpa")
public interface ComponentJpaDao extends JpaRepository<Component, Long>, ComponentDao {
    @Override
    Collection<Component> findByCategory(String category);

}
