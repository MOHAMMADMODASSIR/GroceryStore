package com.grocery.client.service;

import com.grocery.client.entity.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> findAll();

    List<Orders> fetchUserOrders(int id);

    void storeOrders(Orders orders);
}
