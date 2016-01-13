package com.my.order.state;

import com.my.executor.InvalidStateException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */

@Entity
@DiscriminatorValue("CA")
public class OrderStateCancelled extends OrderState {

    public OrderStateCancelled() {
        orderStateType = OrderStateType.CANCELLED;
    }

    @Override
    public void cancel() throws InvalidStateException {

    }

    @Override
    public void send() throws InvalidStateException {

    }

    @Override
    public void pay() throws InvalidStateException {

    }
}
