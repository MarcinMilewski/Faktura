package com.my.warehouse.operative;

import com.my.account.Account;
import com.my.executor.OrderExecutor;
import com.my.executor.OrderUpdateException;
import com.my.order.OrderComponent;
import com.my.warehouse.Warehouse;
import com.my.warehouse.observer.WarehouseOperativeObserved;
import com.my.warehouse.observer.WarehouseOperativeObserver;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@Table(name="WAREHOUSE_OPERATOR")
public class WarehouseOperative implements Serializable, WarehouseOperativeObserved{

    @Transient
    private WarehouseOperativeObserver observer = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "warehouseOperative", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderComponent> orderItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<OrderComponent> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderComponent> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public void registerObserver(WarehouseOperativeObserver warehouseOperativeObserver) {
        observer = warehouseOperativeObserver;
    }

    @Override
    public void unregisterObserver(WarehouseOperativeObserver warehouseOperativeObserver) {
        if (observer.equals(warehouseOperativeObserver)) {
            observer = null;
        }
    }

    @Override
    public void updateOrder(OrderComponent orderComponent) throws OrderUpdateException {
        observer = OrderExecutor.getInstance();
        observer.updateOrder(orderComponent);
    }
}
