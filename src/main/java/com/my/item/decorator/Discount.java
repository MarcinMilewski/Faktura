package com.my.item.decorator;

import com.my.item.Item;
import com.my.item.ItemInterface;

import java.math.BigDecimal;

/**
 * Created by Marcin on 11.01.2016.
 */
public abstract class Discount implements ItemInterface{
    protected Item item;

    public Discount(Item item){
        this.item = item;
    }

    public BigDecimal getPrice(){
        return item.getPrice();
    };
}
