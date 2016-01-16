package com.my.order.send;

import com.my.order.OrderSummary;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@DiscriminatorValue("EX")
public class SendStrategyExpress extends SendStrategy {

    public SendStrategyExpress(){sendStrategyType = SendStrategyType.EXPRESS;}

    @Override
    public void send(OrderSummary orderSummary) {
        sendDate = new Date();
        // wyslij priorytetem
    }
}
