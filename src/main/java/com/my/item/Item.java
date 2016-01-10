package com.my.item;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Marcin on 09.01.2016.
 */
@Entity
@Data
@Table(name = "ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name="NAME", updatable = true, nullable = false)
    private String name;

    @Column(name="PRICE", updatable = true, nullable = false)
    private Float price;

    @Column(name="AMOUNT",updatable = true, nullable = false)
    private Integer amount;

    public Item(){}

    public Item(String name, Float price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
