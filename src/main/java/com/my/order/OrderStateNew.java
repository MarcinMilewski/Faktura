package com.my.order;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 12.01.16.
 */
@Entity
@DiscriminatorValue("NE")
public class OrderStateNew extends OrderState {

    public OrderStateNew() {
        orderStateType = OrderStateType.NEW;
    }


    @Override
    public void cancel() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateCancelled());
    }

    @Override
    public void send() throws InvalidStateException {
        throw new InvalidStateException("New order canont be sent");
    }

    @Override
    public void pay() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateCompletting());

    }

    @Override
    public void complete() throws InvalidStateException {
        throw new InvalidStateException();
    }

    @Override
    public void unableToComplete() throws InvalidStateException {
        throw new InvalidStateException();
    }
}
