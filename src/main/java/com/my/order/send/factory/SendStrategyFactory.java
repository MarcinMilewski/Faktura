package com.my.order.send.factory;

import com.my.order.send.*;

/**
 * Created by marcin on 15.01.16.
 */
public class SendStrategyFactory {
    public static SendStrategy getSendStrategy(SendStrategyType sendStrategyType) {
        if (sendStrategyType.equals(SendStrategyType.ECONOMIC)) {
            return new SendStrategyEconomic();
        }
        else if (sendStrategyType.equals(SendStrategyType.PERSONAL)) {
            return new SendStrategyPersonal();
        }
        else {
            return new SendStrategyExpress();
        }
    }
}
