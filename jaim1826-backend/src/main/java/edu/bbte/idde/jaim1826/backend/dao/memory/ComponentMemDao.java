package edu.bbte.idde.jaim1826.backend.dao.memory;

import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class ComponentMemDao implements ComponentDao {

    private static final Map<Long, Component> COMPONENT_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentMemDao.class);

    @Override
    public void create(Component component) {
        LOGGER.info("Creating component...");
        Long id = ID_GENERATOR.getAndIncrement();
        component.setId(id);
        COMPONENT_MAP.put(id, component);

    }

    @Override
    public Collection<Component> findAll() {
        LOGGER.info("Selecting all components...");
        return COMPONENT_MAP.values();
    }

    @Override
    public Component findById(Long id) {
        LOGGER.info("Selecting component by id...");
        return COMPONENT_MAP.get(id);
    }

    @Override
    public void update(Component component) {
        LOGGER.info("Updating component...");
        COMPONENT_MAP.replace(component.getId(), component);

    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting component...");
        COMPONENT_MAP.remove(id);

    }

    @Override
    public Collection<Component> findAllByCategory(String category) {
        return new ArrayList<>();
    }
}
