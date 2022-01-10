package edu.bbte.idde.jaim1826.backend.dao.jdbc;

import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.dao.DaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.SellerDao;

public class JdbcDaoFactory implements DaoFactory {
    private ComponentDao componentJdbcDao;
    private SellerDao sellerJdbcDao;

    @Override
    public ComponentDao getComponentDao() {
        if (componentJdbcDao == null) {
            componentJdbcDao = new ComponentJdbcDao();
        }
        return componentJdbcDao;
    }

    @Override
    public SellerDao getSellerDao() {
        if (sellerJdbcDao == null) {
            sellerJdbcDao = new SellerJdbcDao();
        }
        return sellerJdbcDao;
    }
}
