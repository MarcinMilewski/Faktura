package com.my.order.state;

import lombok.Data;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 07.01.16.
 */
@Data
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

    public abstract void cancel();

    public abstract void send();

    public abstract void pay();

}
