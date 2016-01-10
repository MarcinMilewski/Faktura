package com.my.order;

import com.my.account.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("O")
public class Order extends OrderComponent{
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Account customer;

    private Date purchaseDate;

    private Date sendDate;

    private Date receivedDate;

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

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Account getCustomer() {
        return customer;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }
}
