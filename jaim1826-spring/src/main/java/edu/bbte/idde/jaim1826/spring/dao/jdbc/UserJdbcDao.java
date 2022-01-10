package edu.bbte.idde.jaim1826.spring.dao.jdbc;

import edu.bbte.idde.jaim1826.spring.dao.UserDao;
import edu.bbte.idde.jaim1826.spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


@Slf4j
@Repository
@Profile("jdbc")
public class UserJdbcDao implements UserDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public Collection<User> findAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = statement.executeQuery();
            return getUsers(resultSet);
        } catch (SQLException e) {
            log.info("Could not find all.");
            throw new SQLException(e);
        }
    }

    @Override
    public User save(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement;
            if(user.getId() != null) {
                statement = connection.prepareStatement("UPDATE user SET username=?, "
                        + "password=?, isAdmin=?, lastLogin=? WHERE id=?");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setBoolean(3, user.getIsAdmin());
                statement.setDate(4, user.getLastLogin());
                statement.setLong(5, user.getId());
                statement.executeUpdate();
            } else {
                statement = connection.prepareStatement("INSERT INTO user(username, "
                                + "password, isAdmin, lastLogin) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setBoolean(3, user.getIsAdmin());
                statement.setDate(4, user.getLastLogin());
                statement.executeUpdate();
            }
            return user;
        } catch (SQLException e) {
            log.info("Could not update.");
            throw new SQLException(e);
        }
    }

    @Override
    public User getById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            Collection<User> components = getUsers(resultSet);

            return components.isEmpty() ? null : components.iterator().next();
        } catch (SQLException e) {
            log.info("Could not find by id.");
            throw new SQLException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id=?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            log.info("Could not delete.");
            throw new SQLException(e);
        }
    }

    private Collection<User> getUsers(ResultSet resultSet) throws SQLException {
        Collection<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("isAdmin"),
                    resultSet.getDate("lastLogin"));
            user.setId(resultSet.getLong(1));
            users.add(user);
        }
        return users;
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            Collection<User> users = getUsers(resultSet);
            return users.isEmpty() ? null : users.iterator().next();

        } catch (SQLException e) {
            log.info("Could not find by id.");
            throw new SQLException(e);
        }
    }
}
