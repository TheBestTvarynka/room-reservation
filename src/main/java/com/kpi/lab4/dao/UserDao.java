package com.kpi.lab4.dao;

import com.kpi.lab4.enums.UserType;
import com.kpi.lab4.entities.User;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UserDao extends GeneralDao {
    public Optional<User> findByUsername(String username) throws SQLException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String findByUsername = "select * from users where username=?";
        Connection connection = getConnection();
        User user = null;
        PreparedStatement pstmt = connection.prepareStatement(findByUsername);
        pstmt.setString(1, username);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            user = new User(
                    UUID.fromString(res.getString("id")),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("full_name"),
                    res.getString("email"),
                    UserType.valueOf(res.getString("role"))
            );
        }
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public void save(User user) throws SQLException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String addNewUser = "insert into users (id, username, password, full_name, email, role) values (?, ?, ?, ?, ?, ?)";
        final String updateUser = "update users set id=?, username=?, password=?, full_name=?, email=? where id=?";
        Connection connection = getConnection();
        PreparedStatement pstmt = null;
        if (user.getId() == null) {
            // create
            user.setId(UUID.randomUUID());
            pstmt = connection.prepareStatement(addNewUser);
        } else {
            // update
            pstmt = connection.prepareStatement(updateUser);
            pstmt.setString(6, user.getId().toString());
        }
        pstmt.setObject(1, user.getId(), java.sql.Types.OTHER);
        pstmt.setString(2, user.getUsername());
        pstmt.setString(3, user.getPassword());
        pstmt.setString(4, user.getFullName());
        pstmt.setString(5, user.getEmail());
        pstmt.setString(6, user.getUserType().name());
        pstmt.execute();
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
    }

}
