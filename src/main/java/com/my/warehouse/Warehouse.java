package com.my.warehouse;

import com.my.item.Item;
import com.my.warehouse.operative.WarehouseOperative;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by marcin on 07.01.16.
 */
@Entity
@Table(name="WAREHOUSE")
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(unique=true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<Item> items;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<WarehouseOperative> warehouseOperatives;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<WarehouseOperative> getWarehouseOperatives() {
        return warehouseOperatives;
    }

    public void setWarehouseOperatives(List<WarehouseOperative> warehouseOperatives) {
        this.warehouseOperatives = warehouseOperatives;
    }
}
