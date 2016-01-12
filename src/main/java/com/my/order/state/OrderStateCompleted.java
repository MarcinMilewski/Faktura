package com.my.order.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("CO")
public class OrderStateCompleted extends OrderState {

    @Override
    public void cancel() {

    }

    @Override
    public void send() {

    }

    @Override
    public void pay() {

    }

    public OrderStateCompleted() {
        orderStateType = OrderStateType.COMPLETED;
    }
}
