package com.my.order;

import com.my.executor.IncorrectOperationException;
import com.my.executor.InvalidStateException;
import com.my.item.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by marcin on 07.01.16.
 */

@Entity
@DiscriminatorValue("OC")
public class OrderItem extends OrderComponent  {

    @OneToOne
    private Item item;

    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    @Override
    public void cancel() throws InvalidStateException, IncorrectOperationException {
        state.cancel();
    }

    @Override
    public void pay() throws InvalidStateException, IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public void send() throws InvalidStateException, IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public OrderItem() {
        super();
    }
}
