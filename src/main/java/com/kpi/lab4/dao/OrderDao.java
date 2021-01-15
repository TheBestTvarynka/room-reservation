package com.kpi.lab4.dao;

import com.kpi.lab4.dto.CreateOrderDto;
import com.kpi.lab4.entities.Order;
import com.kpi.lab4.enums.RoomType;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class OrderDao extends GeneralDao {
    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public UUID createOrder(CreateOrderDto createDto) throws SQLException {
        final String addNewOrder = "insert into orders values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        UUID id = UUID.randomUUID();
        PreparedStatement pstmt = this.connection.prepareStatement(addNewOrder);
        pstmt.setObject(1, id, java.sql.Types.OTHER);
        pstmt.setDate(2, new Date(new java.util.Date().getTime()));
        pstmt.setFloat(3, createDto.getRoom().getPrice());
        pstmt.setString(4, createDto.getRoom().getNumber());
        pstmt.setString(5, createDto.getRoom().getType().name());
        pstmt.setDate(6, new Date(createDto.getDateFrom().getTime()));
        pstmt.setDate(7, new Date(createDto.getDateTo().getTime()));
        pstmt.setString(8, createDto.getPhone());
        pstmt.setBoolean(9, false);
        pstmt.execute();
        return id;
    }

    public Optional<Order> findById(UUID id) throws SQLException {
        final String selectOrderById = "select * from orders where id=?";
        PreparedStatement statement = this.connection.prepareStatement(selectOrderById);
        statement.setObject(1, id, java.sql.Types.OTHER);
        ResultSet res = statement.executeQuery();
        Order order = null;
        if (res.next()) {
            order = new Order(
                    UUID.fromString(res.getString("id")),
                    res.getString("room_number"),
                    res.getDate("creation_date"),
                    res.getFloat("price"),
                    RoomType.valueOf(res.getString("type")),
                    res.getDate("date_from"),
                    res.getDate("date_to"),
                    res.getBoolean("paid")
            );
        }
        return order == null ? Optional.empty() : Optional.of(order);
    }

    public void deleteById(UUID id) throws SQLException {
        final String deleteOrderById = "delete from orders where id=?";
        PreparedStatement statement = this.connection.prepareStatement(deleteOrderById);
        statement.setObject(1, id, java.sql.Types.OTHER);
        statement.execute();
    }
}
