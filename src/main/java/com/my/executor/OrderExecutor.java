package com.my.executor;

import com.google.common.collect.Sets;
import com.my.account.UserServiceFacade;
import com.my.config.SpringContext;
import com.my.order.*;
import com.my.order.repository.OrderRepository;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.observer.WarehouseOperativeObserver;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Created by marcin on 09.01.16.
 */
public class OrderExecutor implements Serializable, WarehouseOperativeObserver {
    private static OrderExecutor instance = new OrderExecutor();

    private static final Logger logger = LoggerFactory.getLogger(OrderExecutor.class);

    private UserServiceFacade userServiceFacade;

    private OrderRepository orderRepository;

    private WarehouseOperativeRepository warehouseOperativeRepository;

    private WarehouseRepository warehouseRepository;

    private OrderExecutor() {
        userServiceFacade = (UserServiceFacade) SpringContext.getApplicationContext().getBean(UserServiceFacade.class);
        orderRepository = SpringContext.getApplicationContext().getBean(OrderRepository.class);
        warehouseOperativeRepository = SpringContext.getApplicationContext().getBean(WarehouseOperativeRepository.class);
        warehouseRepository = SpringContext.getApplicationContext().getBean(WarehouseRepository.class);
        warehouseOperativeRepository.findAll().forEach(warehouseOperative -> registerAsObserver(warehouseOperative));

    }

    public void registerAsObserver(WarehouseOperative warehouseOperative) {
        warehouseOperative.registerObserver(this);
    }

    public static OrderExecutor getInstance() {
        if (instance == null) {
            instance = new OrderExecutor();
        }
        return instance;
    }

    @Override
    public void updateOrder(OrderComponent order) throws OrderUpdateException {
        if (order.getState() instanceof OrderStateCompleted) {
            sendIfWholeCompleted(order);
        }
        else if (order.getState() instanceof OrderStateCancelled) {
            cancelWholeOrder(order);
        }
        else {
            throw new OrderUpdateException();
        }
    }

    private void cancelWholeOrder(OrderComponent order) throws OrderUpdateException {
            try {
                logger.debug("cancelling summary order");
                cancel(order);
                for (OrderComponent child : order.getChildren()) {
                    cancel(child);
                }
            } catch (InvalidStateException e) {
                logger.debug("updateOrder -> invalid state");
                throw new OrderUpdateException();
            } catch (IncorrectOperationException e) {
                logger.debug("updateOrder -> incorrect operation");
                throw new OrderUpdateException();
            }

    }

    private void sendIfWholeCompleted(OrderComponent order) throws OrderUpdateException{
            if (order.getParent().getChildren().stream()
                    .allMatch(orderComponent
                            -> orderComponent.getState() instanceof OrderStateCompleted)) {
                try {
                    send(order.getParent());
                    for (OrderComponent child : order.getChildren()) {
                        send(child);
                    }
                } catch (InvalidStateException e) {
                    logger.debug("updateOrder -> invalid state");
                    throw new OrderUpdateException();
                } catch (IncorrectOperationException e) {
                    logger.debug("updateOrder -> incorrect operation");
                    throw new OrderUpdateException();
                }
            }

    }

    public void send(OrderComponent order) throws InvalidStateException, IncorrectOperationException {
        order.send();
        orderRepository.save(order);
    }

    public void pay(OrderComponent order) throws InvalidStateException, IncorrectOperationException {
        order.pay();
        assignWarehouseOperatives(order);
        for (OrderComponent child : order.getChildren()) {
            child.pay();
        }
        //TODO generacja faktury
        orderRepository.save(order);
    }

    public void cancel(OrderComponent order) throws InvalidStateException, IncorrectOperationException {
        order.cancel();
        unassignWarehouseOperatives(order);
        orderRepository.save(order);
    }


    public void addNew(OrderComponent order) {
        OrderState orderStateNew = new OrderStateNew();
        orderRepository.save(order);
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

    private void unassignWarehouseOperatives(OrderComponent order) {
        Set<Long> orderChildrenIds = order.getChildren().stream()
                .map(orderComponent -> orderComponent.getId()).collect(Collectors.toSet());

//        Set<WarehouseOperative> operatives =
//                warehouseOperativeRepository.findByOrderComponentsIds(orderChildrenIds);
        if (order.isRoot()) {

        }
    }
    public void createInvoice(OrderSummary orderSummary) {

    }
}
