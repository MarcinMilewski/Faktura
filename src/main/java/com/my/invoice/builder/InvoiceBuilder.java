package com.my.invoice.builder;

import com.my.order.OrderComponent;
import com.my.order.OrderSummary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by marcin on 09.01.16.
 */
public interface InvoiceBuilder {


    public void buildSeller();
    public void buildCustomer(String first, String last, String street, String city, int zip);
    public void buildNumber(String number);
    public void buildItems(OrderComponent orders);
    public void buildDates(Date date);
    public void buildTotal(BigDecimal price);
    public String getInvoice();


}
