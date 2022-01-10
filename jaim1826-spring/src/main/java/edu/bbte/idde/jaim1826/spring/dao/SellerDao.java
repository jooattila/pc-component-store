package edu.bbte.idde.jaim1826.spring.dao;

import edu.bbte.idde.jaim1826.spring.model.Seller;

import java.sql.SQLException;
import java.util.Collection;

public interface SellerDao extends Dao<Seller> {
    Collection<Seller> findByCountry(String country) throws SQLException;
}
