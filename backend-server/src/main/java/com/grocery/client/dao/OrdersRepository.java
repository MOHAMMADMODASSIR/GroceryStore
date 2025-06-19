package com.grocery.client.dao;

import com.grocery.client.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByUserId(int id);

    List<Orders> findAll();

}
