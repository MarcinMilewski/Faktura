package com.my.order.send;

import com.my.order.OrderComponent;
import com.my.order.OrderStateType;
import com.my.order.OrderSummary;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by marcin on 15.01.16.
 */
@Entity
@Inheritance(
        strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ORDER_TYPE")
@DiscriminatorOptions(force=true)
@Table(name="ORDER_COMPONENT")
public abstract class SendStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    protected Date sendDate;

    public abstract void send(OrderSummary orderSummary);

    @OneToOne(mappedBy="sendStrategy")
    private OrderComponent orderComponent;

    @Column
    @Enumerated(EnumType.STRING)
    protected SendStrategyType sendStrategyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public OrderComponent getOrderComponent() {
        return orderComponent;
    }

    public void setOrderComponent(OrderComponent orderComponent) {
        this.orderComponent = orderComponent;
    }

    public SendStrategyType getSendStrategyType() {
        return sendStrategyType;
    }
}
