package edu.bbte.idde.jaim1826.spring.dao.mem;

import edu.bbte.idde.jaim1826.spring.dao.UserDao;
import edu.bbte.idde.jaim1826.spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("mem")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class UserMemDao implements UserDao {
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Collection<User> findAll() {
        log.info("Selecting all users...");
        return userMap.values();
    }

    @Override
    public User save(User user) {
        log.info("Creating user...");
        Long id = idGenerator.getAndIncrement();
        if (user.getId() == null) {
            user.setId(id);
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(Long id) {
        log.info("Selecting user by id...");
        return userMap.get(id);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user...");
        userMap.remove(id);
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        log.info("Method not implemented!");
        return null;
    }
}
