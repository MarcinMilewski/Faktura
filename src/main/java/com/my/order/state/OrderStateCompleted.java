package com.my.order.state;

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
    public void cancel() throws UnsupportedOperationException {

    }

    @Override
    public void send() throws UnsupportedOperationException {

    }

    @Override
    public void pay() throws UnsupportedOperationException {

    }
}
