package com.my.order;

import com.my.account.Account;
import com.my.commodity.Commodity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by marcin on 07.01.16.
 */
@Data
@Entity
@DiscriminatorValue("OC")
public class OrderCommodity extends OrderComponent  {

    private Commodity commodity;

    private Integer amount;

    private BigDecimal price;

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
