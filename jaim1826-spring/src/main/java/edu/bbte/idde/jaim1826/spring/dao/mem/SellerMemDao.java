package edu.bbte.idde.jaim1826.spring.dao.mem;

import edu.bbte.idde.jaim1826.spring.dao.SellerDao;
import edu.bbte.idde.jaim1826.spring.model.Seller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("mem")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class SellerMemDao implements SellerDao {

    private final Map<Long, Seller> sellerMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Collection<Seller> findAll() {
        log.info("Selecting all sellers...");
        return sellerMap.values();
    }

    @Override
    public Seller save(Seller seller) {
        log.info("Creating seller...");
        Long id = idGenerator.getAndIncrement();
        if (seller.getId() == null) {
            seller.setId(id);
        }
        sellerMap.put(seller.getId(), seller);
        return seller;
    }

    @Override
    public Seller getById(Long id) {
        log.info("Selecting seller by id...");
        return sellerMap.get(id);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting seller...");
        sellerMap.remove(id);
    }

    @Override
    public Collection<Seller> findByCountry(String country) {
        return new ArrayList<>();
    }
}
