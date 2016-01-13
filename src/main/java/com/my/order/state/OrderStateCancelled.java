package com.my.order.state;

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
    public void cancel() throws UnsupportedOperationException {

    }

    @Override
    public void send() throws UnsupportedOperationException {

    }

    @Override
    public void pay() throws UnsupportedOperationException {

    }
}
