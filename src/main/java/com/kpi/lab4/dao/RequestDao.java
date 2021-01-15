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
    public RequestDao(Connection connection) {
        this.connection = connection;
    }

    public void save(CreateRequestDto createDto) throws SQLException {
        final String addNewRequest = "insert into requests values (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(addNewRequest);
        statement.setObject(1, UUID.randomUUID(), java.sql.Types.OTHER);
        statement.setInt(2, createDto.getSeatNumber());
        statement.setString(3, createDto.getType().name());
        statement.setDate(4, new Date(createDto.getDateFrom().getTime()));
        statement.setDate(5, new Date(createDto.getDateTo().getTime()));
        statement.setString(6, createDto.getPhone());
        statement.execute();
    }

    public List<Request> getAll() throws SQLException, IllegalArgumentException {
        final String selectAllRequests = "select * from requests";
        List<Request> requests = new ArrayList<>();
        Statement statement = this.connection.createStatement();
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
        return requests;
    }

    public Optional<Request> getById(UUID id) throws SQLException {
        final String selectRequestById = "select * from requests where id=?";
        PreparedStatement statement = this.connection.prepareStatement(selectRequestById);
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
        return request == null ? Optional.empty() : Optional.of(request);
    }

    public void deleteRequest(UUID id) throws SQLException {
        final String deleteRequest = "delete from requests where id=?";
        PreparedStatement statement = this.connection.prepareStatement(deleteRequest);
        statement.setObject(1, id, java.sql.Types.OTHER);
        statement.execute();
    }
}
