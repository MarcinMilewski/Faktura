package com.my.warehouse;

import com.my.item.Item;
import com.my.warehouse.operative.WarehouseOperative;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by marcin on 07.01.16.
 */
@Data
@Entity
@Table(name="ORDER_STATE")
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(unique=true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<Item> commodities;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<WarehouseOperative> warehouseOperatives;

}
