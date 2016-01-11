package com.my.order.state;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 11.01.16.
 */
@Data
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
}
