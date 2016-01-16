package com.my.order.send;

import com.my.order.OrderSummary;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@DiscriminatorValue("PE")
public class SendStrategyPersonal extends SendStrategy {

    public SendStrategyPersonal(){sendStrategyType = SendStrategyType.PERSONAL;}

    @Override
    public void send(OrderSummary orderSummary) {
        sendDate = new Date();
        // zachowaj w magazynie do odebrania
    }
}
