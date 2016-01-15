package com.my.invoice.builder;

import com.my.invoice.Invoice;
import com.my.item.Item;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by marcin on 09.01.16.
 */
public class InvoiceTxtBuilder implements InvoiceBuilder{

    private String odstep = "&nbsp&nbsp&nbsp&nbsp";

    private Invoice invoice = new Invoice();

    @Override
    public void buildSeller() {
        invoice.setSeller("Seller: \r\n" +
                "Politechnika Bialostocka Wydzial Informatyki \r\n" +
                "Wiejska 45A \r\n" +
                "11-222 Bialystok \r\n \r\n");
    }

    @Override
    public void buildCustomer(String first, String last, String street, String city, int zip) {
        invoice.setBuyer("Buyer: \r\n" +
                first+" "+last+"\r\n" +
                street +"\r\n" +
                zip +" "+city +"\r\n \r\n");
    }

    @Override
    public void buildNumber(String number) {
        invoice.setNumber("                Invoice for order "+number+"\r\n \r\n");
    }

    @Override
    public void buildItems(OrderComponent orders) {
        int j = 1;

        invoice.setItems(String.format("%20s %20s %20s %20s %20s\r\n","Nr |"," Item name |"," Amount |"," Price |"," Overall price"));
        for(OrderComponent o : orders.getChildren()){
            Item i = ((OrderItem)o).getItem();
            invoice.setItems(invoice.getItems()+
                    "                ---+--------------------+--------------------+--------------------+---------------------\r\n"+
            String.format("%20s %20s %20s %20s %20s\r\n",
                    j++ +" |",
                    " "+i.getName()+" |",
                    " "+((OrderItem) o).getAmount()+" |",
                    " "+((OrderItem) o).getItem().getPrice()+" |",
                    " "+o.getPrice()) );
        }
        invoice.setItems(invoice.getItems() + "\r\n \r\n");
    }

    @Override
    public void buildDates(Date date) {
        String dateNoTime = date.toString();
        dateNoTime = dateNoTime.substring(0,10);
        invoice.setDates("Transaction date: "+ dateNoTime + "\r\n \r\n");
    }

    @Override
    public void buildTotal(BigDecimal price) {
        String prc = String.valueOf(price);
        invoice.setTotal("Total price " +prc);
    }

    @Override
    public String getInvoice() {
        return invoice.getDates()+invoice.getSeller()+
                invoice.getBuyer()+invoice.getNumber()+ invoice.getItems()+invoice.getTotal();
    }
}
