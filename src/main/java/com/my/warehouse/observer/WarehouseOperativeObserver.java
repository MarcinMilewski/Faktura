package com.my.warehouse.observer;

import com.my.executor.OrderUpdateException;
import com.my.order.OrderComponent;

/**
 * Created by marcin on 14.01.16.
 */
public interface WarehouseOperativeObserver {
    void updateOrder(OrderComponent orderComponent) throws OrderUpdateException;
}
