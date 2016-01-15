package com.my.order;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("COMPLETTING")
public class OrderStateCompletting extends OrderState {

    public OrderStateCompletting() {
        orderStateType = OrderStateType.COMPLETTING;
    }

    @Override
    public void cancel() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateCancelled());
    }

    @Override
    public void send() throws InvalidStateException {
        throw new InvalidStateException();

    }

    @Override
    public void pay() throws InvalidStateException {
        throw new InvalidStateException();

    }

    @Override
    public void complete() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateCompleted());
    }

    @Override
    public void unableToComplete() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateUnableToComplete());
    }
}
