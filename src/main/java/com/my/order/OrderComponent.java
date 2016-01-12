package com.my.order;

import com.my.account.Account;
import com.my.order.state.OrderState;
import com.my.warehouse.operative.WarehouseOperative;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by marcin on 06.01.16.
 */
@Entity
@Inheritance(
        strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ORDER_TYPE")
@DiscriminatorOptions(force=true)
@Table(name="ORDER_COMPONENT")
public abstract class OrderComponent implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy="parent",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<OrderComponent> children;

    @ManyToOne
    @JoinColumn
    private OrderComponent parent;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderComponent")
    private OrderState state;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "warehouseOperative_id")
    private WarehouseOperative warehouseOperative;

    @Transient
    public boolean isLeaf() {
        return (children == null || children.size() == 0);
    }

    @Transient
    public boolean isRoot() {
        return (parent == null);
    }

    public abstract void add(OrderComponent orderComponent);
    public abstract void remove(OrderComponent orderComponent);
    public abstract void cancel();
    public abstract void pay();
    public abstract void send(Account account);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<OrderComponent> getChildren() {
        return children;
    }


    public void setChildren(Set<OrderComponent> children) {
        if (children != null) {
            children.stream().forEach(child -> child.setParent(this));
        }
        this.children = children;
    }

    public OrderComponent getParent() {
        return parent;
    }

    public void setParent(OrderComponent parent) {
        this.parent = parent;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarehouseOperative getWarehouseOperative() {
        return warehouseOperative;
    }

    public void setWarehouseOperative(WarehouseOperative warehouseOperative) {
        this.warehouseOperative = warehouseOperative;
    }
}
