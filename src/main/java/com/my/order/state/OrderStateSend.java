package com.my.order.state;

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
    public void cancel() throws UnsupportedOperationException {

    }

    @Override
    public void send() throws UnsupportedOperationException {

    }

    @Override
    public void pay() throws UnsupportedOperationException {

    }
}
