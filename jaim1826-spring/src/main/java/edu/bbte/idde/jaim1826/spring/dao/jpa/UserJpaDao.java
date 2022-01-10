package edu.bbte.idde.jaim1826.spring.dao.jpa;

import edu.bbte.idde.jaim1826.spring.dao.UserDao;
import edu.bbte.idde.jaim1826.spring.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Profile("jpa")
public interface UserJpaDao extends JpaRepository<User, Long>, UserDao {
}
