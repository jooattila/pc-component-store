//package edu.bbte.idde.jaim1826.spring.service;
//
//import edu.bbte.idde.jaim1826.spring.dao.UserDao;
//import edu.bbte.idde.jaim1826.spring.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//
//        User user = null;
//        try {
//            user = userDao.getByUsername(username);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                    user.getPassword(), new ArrayList<>());
//    }
//}
