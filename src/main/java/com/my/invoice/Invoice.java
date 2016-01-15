package com.my.invoice;

import com.my.order.OrderComponent;

import javax.persistence.*;

/**
 * Created by marcin on 09.01.16.
 */
public class Invoice {

    private String items;

    private String dates;

    private String header;

    private String footer;

    private String seller;

    private String buyer;

    private String number;

    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }
}
