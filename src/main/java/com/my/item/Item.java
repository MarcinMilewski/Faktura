package com.my.item;

import com.my.warehouse.Warehouse;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Created by Marcin on 09.01.2016.
 */
@Entity
@Table(name = "ITEM")
public class Item implements ItemInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name="NAME", updatable = true, nullable = false)
    private String name;

    @Column(name="PRICE", updatable = true, nullable = false)
    private BigDecimal price;

    @Column(name="AMOUNT",updatable = true, nullable = false)
    @Min(0)
    private Integer amount;

    @Column(name="WAREHOUSE_AMOUNT", updatable = true, nullable = false)
    @Min(0)
    private Integer warehouseAmount;

    @Column(name="VAT")
    private Integer vat;

    @ManyToOne
    @JoinColumn(name="warehouse_id", nullable = false)
    private Warehouse warehouse;


    public Item(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getWarehouseAmount() {
        return warehouseAmount;
    }

    public void setWarehouseAmount(Integer warehouseAmount) {
        this.warehouseAmount = warehouseAmount;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }
}
