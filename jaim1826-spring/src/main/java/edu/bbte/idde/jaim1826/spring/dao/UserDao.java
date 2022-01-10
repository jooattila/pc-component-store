package edu.bbte.idde.jaim1826.spring.dao;

import edu.bbte.idde.jaim1826.spring.model.User;

import java.sql.SQLException;

public interface UserDao extends Dao<User>{
    User getByUsername(String username) throws SQLException;
}
