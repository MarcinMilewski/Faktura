package com.my.invoice.builder;

import com.my.invoice.Invoice;
import com.my.item.Item;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by marcin on 09.01.16.
 */
public class InvoiceHtmlBuilder implements InvoiceBuilder{

    private Invoice invoice = new Invoice();
    private String odstep = "&nbsp&nbsp&nbsp&nbsp";
    private Float totalTax = 0f;

    public InvoiceHtmlBuilder(){
        invoice.setHeader("<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" xmlns:form=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>Items</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <link href=\"../../../resources/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\" th:href=\"@{/resources/css/bootstrap.min.css}\"/>\n" +
                "    <link href=\"../../../resources/css/core.css\" rel=\"stylesheet\" media=\"screen\" th:href=\"@{/resources/css/core.css}\" />\n" +
                "    <script src=\"http://code.jquery.com/jquery-latest.js\"></script>\n" +
                "    <script src=\"../../../resources/js/bootstrap.min.js\" th:src=\"@{/resources/js/bootstrap.min.js}\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div th:replace=\"fragments/header :: header\">&nbsp;</div>\n");

        invoice.setFooter("</body>\n" + "</html>");
    }

    @Override
    public String getInvoice() {
        return invoice.getHeader()+invoice.getDates()+invoice.getSeller()+
                invoice.getBuyer()+invoice.getNumber()+ invoice.getItems()+invoice.getTotal()+invoice.getFooter();
    }

    @Override
    public void buildSeller() {
        invoice.setSeller("<div class=\"container\">\n" +
                "<table>\n" +
                "<tr><td><b>Seller:</b></td></tr>\n" +
                "<tr><td>Politechnika Białostocka Wydział Informatyki</td></tr>\n" +
                "<tr><td>Wiejska 45A</td></tr>\n" +
                "<tr><td>11-222 Białystok</td></tr>\n" +
                "</table>\n</div><br>");
    }

    @Override
    public void buildCustomer(String first, String last, String street, String city, int zip) {
        invoice.setBuyer("<div class=\"container\">\n" +
                "<table>\n" +
                "<tr><td><b>Buyer:</b></td></tr>\n" +
                "<tr><td>"+first+" "+last+"</td></tr>\n" +
                "<tr><td>"+street+"</td></tr>\n" +
                "<tr><td>"+zip+" "+city+"</td></tr>\n" +
                "</table>\n</div><br><hr />");
    }

    @Override
    public void buildNumber(String number) {
        invoice.setNumber("<div class=\"container\">\n" +
                "<p style=\"text-align: center\">" +
                "<font size=\"30\">Invoice for order "+number+"</font>"+
                "</p>\n"
        );
    }

    @Override
    public void buildItems(OrderComponent orders) {
        int j = 1;


        invoice.setItems("<div class=\"container\">\n" +
                "<table class=\"table-bordered\" width=\"90%\">\n" +
                "<tr>\n" +
                "<th ><b>"+odstep+"Nr"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"Item name"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"Amount"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"Price"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"VAT[%]"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"w/o tax"+odstep+"</b></th>\n" +
                "<th ><b>"+odstep+"Overall"+odstep+"</b></th>\n" +
                "</tr>\n");
        for(OrderComponent o : orders.getChildren()){
            Float tmp = ((OrderItem) o).getItem().getPrice().floatValue() *
                    (((OrderItem) o).getItem().getVat().floatValue()/100);
            BigDecimal tax = new BigDecimal(Float.toString(tmp));
            tax = tax.setScale(2,BigDecimal.ROUND_DOWN);
            Item i = ((OrderItem)o).getItem();
            invoice.setItems(invoice.getItems()+
            "<tr>\n<td> " + j++ + "</td>\n"+
            "<td>" +odstep+ i.getName() +odstep+ "</td>\n"+
                    "<td>" +odstep+ ((OrderItem) o).getAmount() +odstep+ "</td>\n"+
                    "<td>" +odstep+ ((OrderItem) o).getItem().getPrice() +odstep+ "</td>\n"+
                    "<td>" +odstep+ ((OrderItem) o).getItem().getVat() +odstep+ "</td>\n"+
                    "<td>" +odstep+ (((OrderItem) o).getItem().getPrice().floatValue()-tax.floatValue()) +odstep+ "</td>\n"+
                    "<td>" +odstep+ (o.getPrice().floatValue() - (tax.floatValue() * ((OrderItem) o).getAmount().floatValue())) +odstep+ "</td>\n</tr>\n"
            );
            tmp = tax.floatValue() * ((OrderItem) o).getAmount().floatValue();
            totalTax = totalTax + tmp;
        }

        invoice.setItems(invoice.getItems()+"</table>\n");
    }

    @Override
    public void buildDates(Date date) {
        String dateNoTime = date.toString();
        dateNoTime = dateNoTime.substring(0,10);
        invoice.setDates("<div class=\"container\">\n" +
                "<p style=\"text-align: right; width: 90%\">Transaction date:"+
                //odstep+date.toString().replaceAll(String.valueOf(date.getTime()),"")+
                dateNoTime+
                "</p>\n<hr />"
        );
        invoice.setDates(invoice.getDates() + "</div>\n");
    }

    @Override
    public void buildTotal(BigDecimal price) {
        Float totalPrice = price.floatValue() - totalTax;
        BigDecimal rounded = new BigDecimal(Float.toString(totalPrice));
        rounded = rounded.setScale(2,BigDecimal.ROUND_DOWN);
        invoice.setTotal("<br><div class=\"container\">\n" +
                "<table class=\"table-bordered\" width=\"20%\">\n" +
                "<tr>" +
                "<td><b>Price w/o tax</b></td>" +
                "<td><b>VAT</b></td>" +
                "<td><b>with tax</b></td>" +
                "</tr>\n" +
                "<tr>" +
                "<td>"+rounded+"</td>" +
                "<td>"+totalTax+"</td>" +
                "<td>"+price+"</td>" +
                "</tr>\n</table>\n");
    }
}
