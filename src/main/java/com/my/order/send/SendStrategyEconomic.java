package com.my.order.send;

import com.my.order.OrderSummary;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@DiscriminatorValue("EC")
public class SendStrategyEconomic extends SendStrategy {

    public SendStrategyEconomic(){sendStrategyType = SendStrategyType.ECONOMIC;}

    @Override
    public void send(OrderSummary orderSummary) {
        sendDate = new Date();
        // wyslij ekonomiczna przesylka
    }
}
