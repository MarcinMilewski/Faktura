package com.my.order.state;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 12.01.16.
 */
@Data
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
}
