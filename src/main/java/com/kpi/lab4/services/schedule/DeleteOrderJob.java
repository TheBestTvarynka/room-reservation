package com.kpi.lab4.services.schedule;

import com.kpi.lab4.dao.DaoFactory;
import com.kpi.lab4.dao.OrderDao;
import com.kpi.lab4.dao.RoomDao;
import com.kpi.lab4.entities.Order;
import com.kpi.lab4.entities.Room;
import com.kpi.lab4.enums.RoomStatus;
import com.kpi.lab4.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;
import java.util.TimerTask;
import java.util.UUID;

public class DeleteOrderJob extends TimerTask {
    private static Logger logger = LogManager.getLogger(DeleteOrderJob.class);
    private RoomDao roomRepository;
    private OrderDao orderRepository;
    private UUID orderId;

    public DeleteOrderJob(UUID id) throws SQLException {
        this.roomRepository = DaoFactory.createRoomDao();
        this.orderRepository = DaoFactory.createOrderDao();
        this.orderId = id;
    }

    @Override
    public void run() {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isEmpty()) return;
            Order orderData = order.get();
            if (!orderData.getPaid()) {
                Room room = roomRepository.findByRoomNumber(orderData.getRoomNumber()).get();
                roomRepository.updateStatus(room.getId(), RoomStatus.AVAILABLE);
                orderRepository.deleteById(orderId);
            }
            roomRepository.releaseConnection();
            orderRepository.releaseConnection();
        } catch (SQLException e) {
            logger.error("Can't delete unpaid request. Cause: " + e.getMessage());
        }
    }
}
