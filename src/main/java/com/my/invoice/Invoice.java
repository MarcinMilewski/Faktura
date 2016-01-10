package com.my.invoice;

import com.my.order.OrderComponent;

import javax.persistence.*;

/**
 * Created by marcin on 09.01.16.
 */
@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderComponent getOrder() {
        return order;
    }

    public void setOrder(OrderComponent order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
