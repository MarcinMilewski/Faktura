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

    private Invoice invoice = new Invoice();

    private Float totalTax = 0f;

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

        invoice.setItems(String.format("%5s %30s %10s %15s %15s %15s %15s\r\n",
                "Nr |"," Item name |"," Amount |"," Price |"," VAT[%] |"," w/o tax |"," Overall"));
        for(OrderComponent o : orders.getChildren()){
            Float tmp = ((OrderItem) o).getItem().getPrice().floatValue() *
                    (((OrderItem) o).getItem().getVat().floatValue()/100);
            BigDecimal tax = new BigDecimal(Float.toString(tmp));
            tax = tax.setScale(2,BigDecimal.ROUND_DOWN);
            Item i = ((OrderItem)o).getItem();
            invoice.setItems(invoice.getItems()+
                    "----+------------------------------+----------+---------------+---------------+---------------+----------------\r\n"+
            String.format("%5s %30s %10s %15s %15s %15s %15s\r\n",
                    j++ +" |",
                    " "+i.getName()+" |",
                    " "+((OrderItem) o).getAmount()+" |",
                    " "+((OrderItem) o).getItem().getPrice()+" |",
                    " "+((OrderItem) o).getItem().getVat()+" |",
                    " "+(((OrderItem) o).getItem().getPrice().floatValue()-tax.floatValue())+" |",
                    " "+(o.getPrice().floatValue() - (tax.floatValue() * ((OrderItem) o).getAmount().floatValue()))) );
            totalTax = totalTax + tmp;
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
        Float totalPrice = price.floatValue() - totalTax;
        BigDecimal rounded = new BigDecimal(Float.toString(totalPrice));
        rounded = rounded.setScale(2,BigDecimal.ROUND_DOWN);
        invoice.setTotal(String.format(
                "%15s %10s %10s\r\n",
                "Price w/o tax |"," VAT |"," with tax"));
        invoice.setTotal(invoice.getTotal() + "--------------+----------+-----------\r\n");
        invoice.setTotal(invoice.getTotal()+String.format("%15s %10s %10s\r\n",
                rounded+" |",totalTax+" |",price));
    }

    @Override
    public String getInvoice() {
        return invoice.getDates()+invoice.getSeller()+
                invoice.getBuyer()+invoice.getNumber()+ invoice.getItems()+invoice.getTotal();
    }
}
