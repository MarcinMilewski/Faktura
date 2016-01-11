package com.my.item.dto;

/**
 * Created by marcin on 11.01.16.
 */
public class ItemDto {
    private Long id;
    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
