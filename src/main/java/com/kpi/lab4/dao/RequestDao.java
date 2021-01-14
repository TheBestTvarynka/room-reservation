package com.kpi.lab4.dao;

import com.kpi.lab4.dto.CreateRequestDto;
import com.kpi.lab4.entities.Request;
import com.kpi.lab4.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RequestDao extends GeneralDao {
    public void save(CreateRequestDto createDto) throws SQLException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String addNewRequest = "insert into requests values (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(addNewRequest);
        statement.setObject(1, UUID.randomUUID(), java.sql.Types.OTHER);
        statement.setInt(2, createDto.getSeatNumber());
        statement.setString(3, createDto.getType().name());
        statement.setDate(4, new Date(createDto.getDateFrom().getTime()));
        statement.setDate(5, new Date(createDto.getDateTo().getTime()));
        statement.setString(6, createDto.getPhone());
        statement.execute();
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
    }

    public List<Request> getAll() throws SQLException, IllegalArgumentException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String selectAllRequests = "select * from requests";
        List<Request> requests = new ArrayList<>();
        Statement statement = getConnection().createStatement();
        ResultSet res = statement.executeQuery(selectAllRequests);
        while (res.next()) {
            requests.add(new Request(
                    UUID.fromString(res.getString("id")),
                    res.getInt("seat_number"),
                    RoomType.valueOf(res.getString("type")),
                    res.getDate("date_from"),
                    res.getDate("date_to"),
                    res.getString("phone")
            ));
        }
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
        return requests;
    }

    public Optional<Request> getById(UUID id) throws SQLException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String selectRequestById = "select * from requests where id=?";
        PreparedStatement statement = getConnection().prepareStatement(selectRequestById);
        statement.setObject(1, id, java.sql.Types.OTHER);
        ResultSet res = statement.executeQuery();
        Request request = null;
        if (res.next()) {
            request = new Request(
                    UUID.fromString(res.getString("id")),
                    res.getInt("seat_number"),
                    RoomType.valueOf(res.getString("type")),
                    res.getDate("date_from"),
                    res.getDate("date_to"),
                    res.getString("phone")
            );
        }
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
        return request == null ? Optional.empty() : Optional.of(request);
    }

    public void deleteRequest(UUID id) throws SQLException {
        setConnection(SimpleConnectionPool.getPool().getConnection());
        final String deleteRequest = "delete from requests where id=?";
        PreparedStatement statement = getConnection().prepareStatement(deleteRequest);
        statement.setObject(1, id, java.sql.Types.OTHER);
        statement.execute();
        SimpleConnectionPool.getPool().releaseConnection(getConnection());
    }
}
