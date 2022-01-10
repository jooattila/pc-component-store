package edu.bbte.idde.jaim1826.backend.dao.memory;

import edu.bbte.idde.jaim1826.backend.dao.SellerDao;
import edu.bbte.idde.jaim1826.backend.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SellerMemDao implements SellerDao {

    private static final Map<Long, Seller> SELLER_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerMemDao.class);

    @Override
    public void create(Seller seller) {
        LOGGER.info("Creating seller...");
        Long id = ID_GENERATOR.getAndIncrement();
        seller.setId(id);
        SELLER_MAP.put(id, seller);

    }

    @Override
    public Collection<Seller> findAll() {
        LOGGER.info("Selecting all sellers...");
        return SELLER_MAP.values();
    }

    @Override
    public Seller findById(Long id) {
        LOGGER.info("Selecting seller by id...");
        return SELLER_MAP.get(id);
    }

    @Override
    public void update(Seller seller) {
        LOGGER.info("Updating seller...");
        SELLER_MAP.replace(seller.getId(), seller);

    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting seller...");
        SELLER_MAP.remove(id);

    }

    @Override
    public Collection<Seller> findAllByCountry(String country) {
        return new ArrayList<>();
    }
}
