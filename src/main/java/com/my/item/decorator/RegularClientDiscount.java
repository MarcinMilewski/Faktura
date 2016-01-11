package com.my.item.decorator;

import com.my.item.Item;

import java.math.BigDecimal;

/**
 * Created by Marcin on 11.01.2016.
 */
public class RegularClientDiscount extends Discount{

    public RegularClientDiscount(Item item) {
        super(item);
    }

    @Override
    public BigDecimal getPrice(){
        Float price = item.getPrice().floatValue();
        price = price  - (price * 0.05f);
        BigDecimal newPrice = BigDecimal.valueOf(price);
        //item.setPrice(newPrice);
        //BigDecimal newPrice = item.getPrice()-(0.1*item.getPrice());
        return newPrice;
    }
}
