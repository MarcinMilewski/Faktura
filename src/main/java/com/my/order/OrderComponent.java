package com.my.order;

import com.my.account.Account;
import com.my.order.state.OrderState;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by marcin on 06.01.16.
 */
@Data
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

    @OneToOne
    @JoinColumn(name = "orderComponent_id")
    private OrderState state;

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

}
