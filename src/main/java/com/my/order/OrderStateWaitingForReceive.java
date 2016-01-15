package com.my.order;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@DiscriminatorValue("WR")
public class OrderStateWaitingForReceive extends OrderState {

    public OrderStateWaitingForReceive() {
        orderStateType = OrderStateType.WAITING_FOR_RECEIVE;
    }

    @Override
    public void cancel() throws InvalidStateException {
        throw new InvalidStateException();
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
