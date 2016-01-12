package com.my.order.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 11.01.16.
 */
@Entity
@DiscriminatorValue("IN")
public class OrderStateIncompleted extends OrderState {
    @Override
    public void cancel() {

    }

    @Override
    public void send() {

    }

    @Override
    public void pay() {

    }

    public OrderStateIncompleted() {
        orderStateType = OrderStateType.INCOMPLETED;
    }
}
