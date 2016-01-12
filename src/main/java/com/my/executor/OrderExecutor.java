package com.my.executor;

import com.my.account.UserServiceFacade;
import com.my.config.SpringContext;
import com.my.order.OrderSummary;
import com.my.order.OrderComponent;
import com.my.order.repository.OrderRepository;
import com.my.order.state.OrderStateIncompleted;

import java.io.Serializable;

/**
 * Created by marcin on 09.01.16.
 */
public class OrderExecutor implements Serializable{
    private static OrderExecutor instance = null;

    private UserServiceFacade userServiceFacade;

    private OrderRepository orderRepository;

    private OrderExecutor() {
        userServiceFacade = (UserServiceFacade) SpringContext.getApplicationContext().getBean(UserServiceFacade.class);
        orderRepository = SpringContext.getApplicationContext().getBean(OrderRepository.class);
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

    public void createNewOrder(OrderComponent order) {
        order.setState(new OrderStateIncompleted());
        orderRepository.save(order);
    }

    public void createInvoice(OrderSummary orderSummary) {

    }
}
