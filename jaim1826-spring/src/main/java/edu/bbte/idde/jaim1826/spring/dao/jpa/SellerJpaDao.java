package edu.bbte.idde.jaim1826.spring.dao.jpa;

import edu.bbte.idde.jaim1826.spring.dao.SellerDao;
import edu.bbte.idde.jaim1826.spring.model.Seller;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Profile("jpa")
public interface SellerJpaDao extends JpaRepository<Seller, Long>, SellerDao {
    @Override
    Collection<Seller> findByCountry(String country);
}
