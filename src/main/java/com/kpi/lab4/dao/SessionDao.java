package com.kpi.lab4.dao;

import com.kpi.lab4.dto.SessionDto;
import com.kpi.lab4.entities.Session;
import com.kpi.lab4.enums.UserType;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class SessionDao extends GeneralDao {
    public SessionDao(Connection connection) {
        this.connection = connection;
    }

    public void save(SessionDto sessionDto) throws SQLException {
        final String addNewSession = "insert into sessions (id, user_id, role, valid_until, username, csrf_token) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = this.connection.prepareStatement(addNewSession);
        pstmt.setString(1, sessionDto.getSessionId());
        pstmt.setObject(2, sessionDto.getUserId(), java.sql.Types.OTHER);
        pstmt.setString(3, sessionDto.getRole().name());
        pstmt.setTimestamp(4, Timestamp.valueOf(sessionDto.getValidUntil()));
        pstmt.setString(5, sessionDto.getUsername());
        pstmt.setString(6, sessionDto.getCsrfToken());
        pstmt.execute();
    }

    public void update(Session sessionDto) throws SQLException {
        final String updateSession = "update sessions set id=?, user_id=?, role=?, valid_until=?, username=?, csrf_token=? where id=?";
        PreparedStatement pstmt = this.connection.prepareStatement(updateSession);
        pstmt.setString(1, sessionDto.getSessionId());
        pstmt.setObject(2, sessionDto.getUserId(), java.sql.Types.OTHER);
        pstmt.setString(3, sessionDto.getRole().name());
        pstmt.setTimestamp(4, Timestamp.valueOf(sessionDto.getValidUntil()));
        pstmt.setString(5, sessionDto.getUsername());
        pstmt.setString(6, sessionDto.getCsrfToken());
        pstmt.setString(7, sessionDto.getSessionId());
        pstmt.execute();
    }

    public Optional<Session> findById(String sessionId) throws SQLException {
        final String findSessionById = "select * from sessions where id = ?";
        PreparedStatement pstmt = this.connection.prepareStatement(findSessionById);
        pstmt.setString(1, sessionId);
        Session session = null;
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            session = new Session(
                    resultSet.getString(1),
                    UUID.fromString(resultSet.getString(2)),
                    UserType.valueOf(resultSet.getString(3)),
                    resultSet.getTimestamp(4).toLocalDateTime(),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return session == null ? Optional.empty() : Optional.of(session);
    }

    public void delete(String sessionId) throws SQLException {
        final String deleteSessionById = "delete from sessions where id = ?";
        PreparedStatement pstmt = this.connection.prepareStatement(deleteSessionById);
        pstmt.setString(1, sessionId);
        pstmt.execute();
    }
}
