package edu.bbte.idde.jaim1826.backend.dao.jdbc;

import edu.bbte.idde.jaim1826.backend.dao.SellerDao;
import edu.bbte.idde.jaim1826.backend.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SellerJdbcDao implements SellerDao {

    private Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentJdbcDao.class);

    @Override
    public void create(Seller seller) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO seller(name, country, isCompany) VALUES (?, ?, ?)");
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getCountry());
            statement.setBoolean(3, seller.getIsCompany());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Couldn't create.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public Collection<Seller> findAll() {
        Collection<Seller> sellers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller");
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info(resultSet.toString());
            sellers = getSellers(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Couldn't find all.", e);
        }
        return sellers;
    }

    @Override
    public Seller findById(Long id) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getSellers(resultSet).iterator().next();
        } catch (SQLException e) {
            LOGGER.error("Couldn't find by id.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Seller seller) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE seller SET name=?, "
                    + "country=?, isCompany=? WHERE id=?");
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getCountry());
            statement.setBoolean(3, seller.getIsCompany());
            statement.setLong(4, seller.getId());

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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM seller WHERE id = ?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Couldn't delete.", e);
        }
    }

    @Override
    public Collection<Seller> findAllByCountry(String country) {
        Collection<Seller> sellers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller WHERE country=?");
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info(resultSet.toString());
            sellers = getSellers(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Couldn't find all by country.", e);
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return sellers;
    }

    public Collection<Seller> getSellers(ResultSet resultSet) throws SQLException {
        Collection<Seller> sellers = new ArrayList<>();
        while (resultSet.next()) {
            Seller seller = new Seller(resultSet.getString("name"),
                    resultSet.getString("country"),
                    resultSet.getBoolean("isCompany"));
            seller.setId(resultSet.getLong(1));
            sellers.add(seller);
        }
        return sellers;
    }
}
