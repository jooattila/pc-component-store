package edu.bbte.idde.jaim1826.backend.dao.memory;

import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.dao.DaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.SellerDao;

public class MemDaoFactory implements DaoFactory {
    private ComponentDao memComponentDao;
    private SellerDao memSellerDao;

    @Override
    public ComponentDao getComponentDao() {
        if (memComponentDao == null) {
            memComponentDao = new ComponentMemDao();
        }
        return memComponentDao;
    }

    @Override
    public SellerDao getSellerDao() {
        if (memSellerDao == null) {
            memSellerDao = new SellerMemDao();
        }
        return memSellerDao;
    }
}
