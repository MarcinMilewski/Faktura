package com.my.executor;

import com.google.common.collect.Sets;
import com.my.account.UserServiceFacade;
import com.my.config.SpringContext;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;
import com.my.order.OrderSummary;
import com.my.order.repository.OrderRepository;
import com.my.order.state.OrderState;
import com.my.order.state.OrderStateNew;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by marcin on 09.01.16.
 */
public class OrderExecutor implements Serializable{
    private static OrderExecutor instance = null;

    private UserServiceFacade userServiceFacade;

    private OrderRepository orderRepository;

    private WarehouseOperativeRepository warehouseOperativeRepository;

    private WarehouseRepository warehouseRepository;

    private OrderExecutor() {
        userServiceFacade = (UserServiceFacade) SpringContext.getApplicationContext().getBean(UserServiceFacade.class);
        orderRepository = SpringContext.getApplicationContext().getBean(OrderRepository.class);
        warehouseOperativeRepository = SpringContext.getApplicationContext().getBean(WarehouseOperativeRepository.class);
        warehouseRepository = SpringContext.getApplicationContext().getBean(WarehouseRepository.class);
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
        order.pay();
        assignWarehouseOperatives(order);
    }

    public void cancel(OrderComponent order) {

    }

    public void addNew(OrderComponent order) {
        OrderState orderStateNew = new OrderStateNew();
        orderRepository.save(order);
        assignWarehouseOperatives(order);
    }

    private void assignWarehouseOperatives(OrderComponent order) {
        Map<WarehouseOperative, Set<OrderComponent>> operativeOrderItemMap = new HashMap<>();

        Iterable<WarehouseOperative> warehouseOperatives = warehouseOperativeRepository.findAll();

        for (OrderComponent orderComponent : order.getChildren()) {
            OrderItem orderItem = (OrderItem) orderComponent;
            WarehouseOperative operative = orderItem.getItem().getWarehouse().getWarehouseOperatives().get(0);

            if (operativeOrderItemMap.containsKey(operative)) {
                operativeOrderItemMap.get(operative).add(orderItem);
            }
            else {
                operativeOrderItemMap.put(operative, Sets.newHashSet());
                operativeOrderItemMap.get(operative).add(orderItem);
            }
        }
        operativeOrderItemMap.entrySet().stream().forEach(entry ->
                entry.getValue().stream().forEach(itemOrder -> itemOrder.setWarehouseOperative(entry.getKey())));
        Set<OrderComponent> components = operativeOrderItemMap.values().stream().flatMap(orderComponents -> orderComponents.stream()).collect(Collectors.toSet());
        orderRepository.save(components);
    }

    public void createInvoice(OrderSummary orderSummary) {

    }
}
