package com.my.invoice.builder;

/**
 * Created by marcin on 09.01.16.
 */
public interface InvoiceBuilder {

    public void buildSeller();
    public void buildCustomer();
    public void buildNumber();
    public void buildItems();
    public void buildVAT();
    public void buildAmount();
    public void buildDates();


}
