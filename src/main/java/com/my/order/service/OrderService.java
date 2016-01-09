package com.my.order.service;

import com.my.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by marcin on 09.01.16.
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public OrderRepository getRepository() {
        return orderRepository;
    }
}
