package com.my.order;

import com.my.account.Account;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Data
@Entity
@DiscriminatorValue("O")
public class Order extends OrderComponent{
    private Long totalPrice;

    private Account customer;

    @Override
    public void add(OrderComponent orderComponent) {

    }

    @Override
    public void remove(OrderComponent orderComponent) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void pay() {

    }

    @Override
    public void send(Account account) {

    }
}
