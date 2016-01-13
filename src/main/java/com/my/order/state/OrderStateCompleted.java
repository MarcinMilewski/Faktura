package com.my.order.state;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("CO")
public class OrderStateCompleted extends OrderState {
    
    public OrderStateCompleted() {
        orderStateType = OrderStateType.COMPLETED;
    }

    @Override
    public void cancel() throws InvalidStateException {
        throw new InvalidStateException();

    }

    @Override
    public void send() throws InvalidStateException {

    }

    @Override
    public void pay() throws InvalidStateException {
        throw new InvalidStateException();

    }
}
