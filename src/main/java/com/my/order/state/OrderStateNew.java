package com.my.order.state;

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

    }

    @Override
    public void send() throws InvalidStateException {

    }

    @Override
    public void pay() throws InvalidStateException {

    }
}
