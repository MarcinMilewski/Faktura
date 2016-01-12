package com.my.order.state;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 12.01.16.
 */
@Entity
@DiscriminatorValue("NE")
public class OrderStateNew extends OrderState {
    @Override
    public void cancel() {

    }

    @Override
    public void send() {

    }

    @Override
    public void pay() {

    }

    public OrderStateNew() {
        orderStateType = OrderStateType.NEW;
    }
}
