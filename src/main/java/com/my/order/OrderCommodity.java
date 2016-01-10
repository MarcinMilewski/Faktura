package com.my.order;

import com.my.account.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by marcin on 07.01.16.
 */

@Entity
@DiscriminatorValue("OC")
public class OrderCommodity extends OrderComponent  {


    private Integer amount;

    private BigDecimal price;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


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
