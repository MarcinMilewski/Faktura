package com.my.item.cart;

import com.my.item.Item;
import com.my.item.dto.ItemDto;
import com.my.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by marcin on 11.01.16.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ItemCart {

    @Autowired
    private ItemRepository itemRepository;
    private List<ItemDto> cart;

    public List<ItemDto> getCart() {
        return cart;
    }

    public void setCart(List<ItemDto> cart) {
        this.cart = cart;
    }

    public void addToCart(ItemDto itemDto) {
        for (ItemDto cartItem : cart) {
            if (cartItem.getId().equals(itemDto.getId())) {
                cartItem.setAmount(cartItem.getAmount() + itemDto.getAmount());
                return;
            }
        }
        cart.add(itemDto);
    }

    @PostConstruct
    protected void initialize() {
        cart = new ArrayList<>();
    }

    public void clear() {
        cart.clear();
    }

    public void clearAndReturnItems() {
            Set<Long> itemsIds = cart.stream().map(i -> i.getId()).collect(Collectors.toSet());
            Iterable<Item> items = itemRepository.findAll(itemsIds);

            Map<Item, ItemDto> itemDtoMap = StreamSupport.stream(items.spliterator(), false)
                    .map(item -> new AbstractMap.SimpleImmutableEntry<>(item,
                            cart.stream().filter(itemDto -> item.getId().equals(itemDto.getId()))
                    .findFirst().get())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Item, ItemDto> itemItemDtoEntry : itemDtoMap.entrySet()) {
            Item item = itemItemDtoEntry.getKey();
            ItemDto dto = itemItemDtoEntry.getValue();
            item.setAmount(item.getAmount() + dto.getAmount());
        }

        itemRepository.save(items);
    }

    @PreDestroy
    public void preDestroy() {
        clear();
    }
}
