package edu.bbte.idde.jaim1826.backend.dao;

public interface DaoFactory {
    ComponentDao getComponentDao();

    SellerDao getSellerDao();
}
