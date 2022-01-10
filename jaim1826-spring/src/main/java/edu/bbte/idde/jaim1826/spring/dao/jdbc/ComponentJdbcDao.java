package edu.bbte.idde.jaim1826.spring.dao.jdbc;

import edu.bbte.idde.jaim1826.spring.dao.ComponentDao;
import edu.bbte.idde.jaim1826.spring.model.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Repository
@Profile("jdbc")
public class ComponentJdbcDao implements ComponentDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Component> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component");
            ResultSet resultSet = statement.executeQuery();
            return getComponents(resultSet);
        } catch (SQLException e) {
            log.info("Could not find all.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Component getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Collection<Component> components = getComponents(resultSet);

            return components.isEmpty() ? null : components.iterator().next();
        } catch (SQLException e) {
            log.info("Could not find by id.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Component save(Component component) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement;

            if(component.getId() != null) {
                statement = connection.prepareStatement("UPDATE component SET category=?, "
                        + "model=?, releaseYear=?, price=?, availability=? WHERE id=?");
                statement.setString(1, component.getCategory());
                statement.setString(2, component.getModel());
                statement.setInt(3, component.getReleaseYear());
                statement.setDouble(4, component.getPrice());
                statement.setBoolean(5, component.getAvailability());
                statement.setLong(6, component.getId());
                statement.executeUpdate();
            } else {
                statement = connection.prepareStatement("INSERT INTO component(category, "
                                + "model, releaseYear, price, availability) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, component.getCategory());
                statement.setString(2, component.getModel());
                statement.setInt(3, component.getReleaseYear());
                statement.setDouble(4, component.getPrice());
                statement.setBoolean(5, component.getAvailability());
                statement.executeUpdate();
            }
            return component;
        } catch (SQLException e) {
            log.info("Could not update.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM component WHERE id=?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            log.info("Could not delete.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Collection<Component> findByCategory(String category) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component where category=?");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            return getComponents(resultSet);
        } catch (SQLException e) {
            log.info("Could not find by category.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Collection<Component> getComponents(ResultSet resultSet) throws SQLException {
        Collection<Component> components = new ArrayList<>();
        while (resultSet.next()) {
            Component component = new Component(resultSet.getString("category"),
                    resultSet.getString("model"),
                    resultSet.getInt("releaseYear"),
                    resultSet.getDouble("price"),
                    resultSet.getBoolean("availability"));
            component.setId(resultSet.getLong(1));

            components.add(component);
        }
        return components;
    }
}
