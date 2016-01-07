package com.my.order;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@Table(name="ORDER_STATE")
public class OrderState implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
}
