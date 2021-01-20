package com.kpi.lab4.services;

import com.kpi.lab4.dao.UserDao;
import com.kpi.lab4.dto.LoginDto;
import com.kpi.lab4.dto.RegisterDto;
import com.kpi.lab4.entities.User;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.exception.UserAlreadyExistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class UserService implements Service {
    private static Logger logger = LogManager.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(LoginDto credentials) throws UnavailableException {
        Optional<User> user;
        try {
            user = userDao.findByUsername(credentials.getUsername());
        } catch(SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
        if (user.isPresent() && BCrypt.checkpw(credentials.getPassword(), user.get().getPassword())) {
            return user.get();
        }
        return null;
    }

    public void register(RegisterDto registerData) throws UnavailableException {
        try {
            if (userDao.findByUsername(registerData.getUsername()).isPresent()) {
                throw new UserAlreadyExistException();
            }
            registerData.setPassword(BCrypt.hashpw(registerData.getPassword(), BCrypt.gensalt()));
            userDao.save(User.fromRegisterData(registerData));
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
    }

    @Override
    public void finish() {
        userDao.releaseConnection();
    }
}
