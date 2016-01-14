package com.my.warehouse.observer;

import com.my.executor.OrderUpdateException;
import com.my.order.OrderComponent;

/**
 * Created by marcin on 14.01.16.
 */
public interface WarehouseOperativeObserved {
    void registerObserver(WarehouseOperativeObserver warehouseOperativeObserver);
    void unregisterObserver(WarehouseOperativeObserver warehouseOperativeObserver);
    void updateOrder(OrderComponent orderComponent) throws OrderUpdateException;
}
