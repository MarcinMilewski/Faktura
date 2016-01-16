package com.my.item.form;

import com.my.item.Item;
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

    private int vat;

    @NotBlank
    private Long warehouseId;

    private Boolean occasional;
    private Boolean twoinone;

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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Boolean getOccasional() {
        return occasional;
    }

    public void setOccasional(Boolean occasional) {
        this.occasional = occasional;
    }

    public Boolean getTwoinone() {
        return twoinone;
    }

    public void setTwoinone(Boolean twoinone) {
        this.twoinone = twoinone;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }
}
