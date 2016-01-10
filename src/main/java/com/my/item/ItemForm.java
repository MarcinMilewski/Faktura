package com.my.item;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * Created by Marcin on 09.01.2016.
 */
public class ItemForm {

    @NotBlank
    private String name;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private int amount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Item createItem(){
        return new Item(name,price,amount);
    }

}
