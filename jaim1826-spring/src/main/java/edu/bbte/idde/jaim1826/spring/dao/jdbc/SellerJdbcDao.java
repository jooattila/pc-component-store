package edu.bbte.idde.jaim1826.spring.dao.jdbc;

import edu.bbte.idde.jaim1826.spring.dao.SellerDao;
import edu.bbte.idde.jaim1826.spring.model.Seller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Repository
@Profile("jdbc")
public class SellerJdbcDao implements SellerDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<Seller> findAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller");
            ResultSet resultSet = statement.executeQuery();
            return getSellers(resultSet);
        } catch (SQLException e) {
            log.info("Could not find all.");
            throw new SQLException(e);
        }
    }

    @Override
    public Seller getById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Collection<Seller> sellers = getSellers(resultSet);
            return sellers.isEmpty() ? null : sellers.iterator().next();
        } catch (SQLException e) {
            log.info("Could not find by id.");
            throw new SQLException(e);
        }
    }

    @Override
    public Seller save(Seller seller) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE seller SET name=?, "
                    + "country=?, isCompany=? WHERE id=?");
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getCountry());
            statement.setBoolean(3, seller.getIsCompany());
            statement.setLong(4, seller.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                statement = connection.prepareStatement(
                        "INSERT INTO seller(name, country, isCompany) VALUES (?, ?, ?)");
                statement.setString(1, seller.getName());
                statement.setString(2, seller.getCountry());
                statement.setBoolean(3, seller.getIsCompany());
                statement.executeUpdate();
            }
            return seller;
        } catch (SQLException e) {
            log.info("Could not update.");
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM seller WHERE id = ?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            log.info("Could not delete.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Collection<Seller> findByCountry(String country) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM seller WHERE country=?");
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();
            return getSellers(resultSet);
        } catch (SQLException e) {
            log.info("Could not find by country.");
            throw new SQLException(e);
        }
    }

    private Collection<Seller> getSellers(ResultSet resultSet) throws SQLException {
        Collection<Seller> sellers = new ArrayList<>();
        while (resultSet.next()) {
            Seller seller = new Seller(resultSet.getString("name"),
                    resultSet.getString("country"),
                    resultSet.getBoolean("isCompany"), null);
            seller.setId(resultSet.getLong(1));
            sellers.add(seller);
        }
        return sellers;
    }
}
