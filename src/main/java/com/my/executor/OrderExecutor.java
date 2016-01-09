package com.my.executor;

import com.my.account.UserService;
import com.my.config.SpringContext;
import com.my.order.Order;
import com.my.order.OrderComponent;

import java.io.Serializable;

/**
 * Created by marcin on 09.01.16.
 */
public class OrderExecutor implements Serializable{
    private static OrderExecutor instance = null;

    private UserService userService;


    private OrderExecutor() {
        userService = (UserService) SpringContext.getApplicationContext().getBean(UserService.class);

    }

    public static OrderExecutor getInstance() {
        if (instance == null) {
            instance = new OrderExecutor();

        }
        return instance;
    }

    public void updateOrder(OrderComponent order) {

    }

    public void send(OrderComponent order) {

    }

    public void pay(OrderComponent order) {

    }

    public void cancel(OrderComponent order) {

    }

    public void createOrder(OrderComponent order) {

    }

    public void createInvoice(Order order) {

    }
}
