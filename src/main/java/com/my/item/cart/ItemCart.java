package com.my.item.cart;

import com.my.item.dto.ItemDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcin on 11.01.16.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ItemCart {
    private List<ItemDto> cart;

    public List<ItemDto> getCart() {
        return cart;
    }

    public void setCart(List<ItemDto> cart) {
        this.cart = cart;
    }

    public void addToCart(ItemDto itemDto) {
        cart.add(itemDto);
    }

    @PostConstruct
    protected void initialize() {
        cart = new ArrayList<>();
    }
}
