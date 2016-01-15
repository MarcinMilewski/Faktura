package com.my.order;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@DiscriminatorValue("UN")
public class OrderStateUnableToComplete extends OrderState {

    public OrderStateUnableToComplete() {
        orderStateType = OrderStateType.UNABLE_TO_COMPLETE;
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
        throw new InvalidStateException();
    }

    @Override
    public void unableToComplete() throws InvalidStateException {
        throw new InvalidStateException();
    }
}
