package com.my.invoice;

import com.my.order.OrderComponent;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by marcin on 09.01.16.
 */
@Entity
@Data
@Table(name = "INVOICE")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderComponent order;

    @Lob
    private String content;

}
