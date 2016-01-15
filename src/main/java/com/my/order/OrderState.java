package com.my.order;

import com.my.executor.InvalidStateException;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@Inheritance(
        strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ORDER_STATE_TYPE")
@DiscriminatorOptions(force=true)
@Table(name="ORDER_STATE")
public abstract class OrderState implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "state")
    private OrderComponent orderComponent;

    @Column
    @Enumerated(EnumType.STRING)
    protected OrderStateType orderStateType;

    public abstract void cancel() throws InvalidStateException;

    public abstract void send() throws InvalidStateException;

    public abstract void pay() throws InvalidStateException;

    public abstract void complete() throws InvalidStateException;

    public abstract void unableToComplete() throws InvalidStateException;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderComponent getOrderComponent() {
        return orderComponent;
    }

    public void setOrderComponent(OrderComponent orderComponent) {
        this.orderComponent = orderComponent;
    }

    public OrderStateType getOrderStateType() {
        return orderStateType;
    }
}
