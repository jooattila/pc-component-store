package edu.bbte.idde.jaim1826.spring.dao.mem;

import edu.bbte.idde.jaim1826.spring.dao.ComponentDao;
import edu.bbte.idde.jaim1826.spring.model.Component;
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

@Repository
@Slf4j
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Profile("mem")

public class ComponentMemDao implements ComponentDao {
    private final Map<Long, Component> componentMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Collection<Component> findAll() {
        log.info("Selecting all components...");
        return componentMap.values();
    }

    @Override
    public Component getById(Long id) {
        log.info("Selecting component by id...");
        return componentMap.get(id);
    }

    @Override
    public Component save(Component component) {
        log.info("Creating component...");
        Long id = idGenerator.getAndIncrement();
        if (component.getId() == null) {
            component.setId(id);
        }
        componentMap.put(component.getId(), component);

        return component;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting component...");
        componentMap.remove(id);
    }

    @Override
    public Collection<Component> findByCategory(String category) {
        return new ArrayList<>();
    }
}
