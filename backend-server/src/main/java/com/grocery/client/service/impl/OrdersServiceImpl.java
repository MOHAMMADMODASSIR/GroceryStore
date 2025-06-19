package com.grocery.client.service.impl;

import com.grocery.client.dao.OrdersRepository;
import com.grocery.client.entity.Orders;
import com.grocery.client.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public List<Orders> findAll() {
        List<Orders> ordersList = ordersRepository.findAll();
        return ordersList;
    }

    @Override
    public List<Orders> fetchUserOrders(int id) {
        List<Orders> ordersList = ordersRepository.findByUserId(id);
        return ordersList;
    }

    @Override
    public void storeOrders(Orders orders) {
        ordersRepository.save(orders);
    }
}
