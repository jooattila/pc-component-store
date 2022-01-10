package edu.bbte.idde.jaim1826.backend.dao;

import edu.bbte.idde.jaim1826.backend.model.Seller;

import java.util.Collection;

public interface SellerDao extends Dao<Seller> {
    Collection<Seller> findAllByCountry(String country);
}
