package com.kpi.lab4.services;

import com.kpi.lab4.dao.OrderDao;
import com.kpi.lab4.dao.RequestDao;
import com.kpi.lab4.dao.RoomDao;
import com.kpi.lab4.dao.UserDao;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public UserService createUserService() {
        return new UserService(new UserDao());
    }

    @Override
    public OrderService createOrderService() {
        return new OrderService(new OrderDao(), new RoomDao());
    }

    @Override
    public RequestService createRequestService() {
        return new RequestService(new RequestDao(), new RoomDao(), new OrderDao());
    }

}
