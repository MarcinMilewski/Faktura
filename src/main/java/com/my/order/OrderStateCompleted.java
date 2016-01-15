package com.my.order;

import com.my.executor.InvalidStateException;
import com.my.order.send.SendStrategyPersonal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@DiscriminatorValue("CO")
public class OrderStateCompleted extends OrderState {
    
    public OrderStateCompleted() {
        orderStateType = OrderStateType.COMPLETED;
    }

    @Override
    public void cancel() throws InvalidStateException {
        getOrderComponent().setState(new OrderStateCancelled());
    }

    @Override
    public void send() throws InvalidStateException {
        if (getOrderComponent().getSendStrategy() != null &&
                getOrderComponent().getSendStrategy() instanceof SendStrategyPersonal) {
            getOrderComponent().setState(new OrderStateWaitingForReceive());
            getOrderComponent().getChildren().stream()
                    .forEach(orderComponent -> orderComponent.setState(new OrderStateWaitingForReceive()));
        }
        else {
            getOrderComponent().setState(new OrderStateSend());
        }
    }

    @Override
    public void pay() throws InvalidStateException {
        throw new InvalidStateException();
    }

    @Override
    public void complete() throws InvalidStateException {
        throw new InvalidStateException();
    }

    @Override
    public void unableToComplete() throws InvalidStateException {
        throw new InvalidStateException();
    }
}
