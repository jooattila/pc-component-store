package edu.bbte.idde.jaim1826.backend.dao.jdbc;

import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ComponentJdbcDao implements ComponentDao {

    private Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentJdbcDao.class);

    @Override
    public Collection<Component> findAll() {
        Collection<Component> components = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component");
            ResultSet resultSet = statement.executeQuery();
            components = getComponents(resultSet);

        } catch (SQLException e) {
            LOGGER.error("Couldn't find all.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return components;
    }

    @Override
    public Component findById(Long id) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getComponents(resultSet).iterator().next();
        } catch (SQLException e) {
            LOGGER.error("Couldn't find by id.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Component> findAllByCategory(String category) {
        Collection<Component> components = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM component where category=?");
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            components = getComponents(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Couldn't find all.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return components;
    }

    @Override
    public void create(Component component) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO component(category, "
                    + "model, releaseYear, price, availability, sellerId) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, component.getCategory());
            statement.setString(2, component.getModel());
            statement.setInt(3, component.getReleaseYear());
            statement.setDouble(4, component.getPrice());
            statement.setBoolean(5, component.getAvailability());
            statement.setLong(6, component.getSellerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Couldn't create.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void update(Component component) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE component SET category=?, "
                    + "model=?, releaseYear=?, price=?, availability=?, sellerId=? WHERE id=?");
            statement.setString(1, component.getCategory());
            statement.setString(2, component.getModel());
            statement.setInt(3, component.getReleaseYear());
            statement.setDouble(4, component.getPrice());
            statement.setBoolean(5, component.getAvailability());
            statement.setLong(6, component.getSellerId());
            statement.setLong(7, component.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Couldn't update.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    @Override
    public void delete(Long id) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM component WHERE id=?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Couldn't delete.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    public Collection<Component> getComponents(ResultSet resultSet) throws SQLException {
        Collection<Component> components = new ArrayList<>();
        while (resultSet.next()) {
            Component component = new Component(resultSet.getString("category"),
                    resultSet.getString("model"),
                    resultSet.getInt("releaseYear"),
                    resultSet.getDouble("price"),
                    resultSet.getBoolean("availability"), resultSet.getLong("sellerId"));
            component.setId(resultSet.getLong(1));
            components.add(component);
        }
        return components;
    }
}
