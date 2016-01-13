package com.my.order.state;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("PA")
public class OrderStatePaid extends OrderState {

    public OrderStatePaid() {
        orderStateType = OrderStateType.PAID;
    }


    @Override
    public void cancel() throws InvalidStateException {
        throw new InvalidStateException("Cannot cancel paid order");
    }

    @Override
    public void send() throws InvalidStateException {
        throw new InvalidStateException("Paid order cannot be send, before completed");
    }

    @Override
    public void pay() throws InvalidStateException {
        throw new InvalidStateException("Paid cannot be paid second time");

    }
}
