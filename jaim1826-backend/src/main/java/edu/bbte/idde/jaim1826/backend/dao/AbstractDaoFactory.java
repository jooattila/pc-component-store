package edu.bbte.idde.jaim1826.backend.dao;

import edu.bbte.idde.jaim1826.backend.config.ConfigFactory;
import edu.bbte.idde.jaim1826.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.memory.MemDaoFactory;

public class AbstractDaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getDaoFactory() {
        synchronized (DaoFactory.class) {
            if (daoFactory == null) {
                String daoType = ConfigFactory.getConfig().getDaoType();
                if ("jdbc".equals(daoType)) {
                    daoFactory = new JdbcDaoFactory();
                } else {
                    daoFactory = new MemDaoFactory();
                }
            }
            return daoFactory;
        }
    }
}
