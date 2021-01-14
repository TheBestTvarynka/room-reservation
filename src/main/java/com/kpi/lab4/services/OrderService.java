package com.kpi.lab4.services;

import com.kpi.lab4.dao.OrderDao;
import com.kpi.lab4.dao.RoomDao;
import com.kpi.lab4.dto.CreateOrderDto;
import com.kpi.lab4.entities.Room;
import com.kpi.lab4.enums.RoomStatus;
import com.kpi.lab4.exception.AlreadyBookedException;
import com.kpi.lab4.exception.BookNotFoundException;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.services.schedule.DeleteOrderJob;
import com.kpi.lab4.services.schedule.Scheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
    private static Logger logger = LogManager.getLogger(OrderService.class);

    private OrderDao orderRepository;
    private RoomDao roomRepository;

    public OrderService(OrderDao orderDao, RoomDao roomDao) {
        this.orderRepository = orderDao;
        this.roomRepository = roomDao;
    }

    public void bookRoom(CreateOrderDto createDto)
            throws UnavailableException, BookNotFoundException, AlreadyBookedException {
        UUID id;
        try {
            Optional<Room> room = roomRepository.findByRoomNumber(createDto.getRoomNumber());
            if (room.isPresent()) {
                if (!room.get().getStatus().equals(RoomStatus.AVAILABLE)) {
                    throw new AlreadyBookedException();
                }
                roomRepository.updateStatus(room.get().getId(), RoomStatus.BOOKED);
            }
            createDto.setRoom(room.orElseThrow(BookNotFoundException::new));
            id = orderRepository.createOrder(createDto);
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }

        Date date = new Date();
        // 2 * 24 * 60 * 60 * 1000 = 172_800_000 = 2 days
        date.setTime(date.getTime() + 172_800_000L);
        Scheduler.scheduleJob(new DeleteOrderJob(id), date);
    }
}
