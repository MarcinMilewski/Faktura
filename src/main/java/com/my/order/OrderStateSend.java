package com.my.order;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("SE")
public class OrderStateSend extends OrderState {

    public OrderStateSend() {
        orderStateType = OrderStateType.SEND;
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
}
