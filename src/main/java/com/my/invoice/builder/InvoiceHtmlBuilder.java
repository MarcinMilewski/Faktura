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
                "<th width=\"5%\"><b>"+odstep+"Nr"+odstep+"</b></th>\n" +
                "<th width=\"40%\"><b>"+odstep+"Item name"+odstep+"</b></th>\n" +
                "<th width=\"15%\"><b>"+odstep+"Amount"+odstep+"</b></th>\n" +
                "<th width=\"20%\"><b>"+odstep+"Price"+odstep+"</b></th>\n" +
                "<th width=\"20%\"><b>"+odstep+"Overall price"+odstep+"</b></th>\n" +
                "</tr>\n");
        for(OrderComponent o : orders.getChildren()){
            Item i = ((OrderItem)o).getItem();
            invoice.setItems(invoice.getItems()+
            "<tr>\n<td>" + j++ + "</td>\n"+
            "<td>" +odstep+ i.getName() +odstep+ "</td>\n"+
                    "<td>" +odstep+ ((OrderItem) o).getAmount() +odstep+ "</td>\n"+
                    "<td>" +odstep+ ((OrderItem) o).getItem().getPrice() +odstep+ "</td>\n"+
                    "<td>" +odstep+ o.getPrice() +odstep+ "</td>\n</tr>\n"
            );

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
        String prc = String.valueOf(price);
        invoice.setTotal("<br><div class=\"container\">\n" +
                "<table class=\"table-bordered\" width=\"20%\">\n" +
                "<tr>" +
                "<td><b>Total price</b></td>" +
                "</tr>\n" +
                "<tr>" +
                "<td>"+prc+"</td>" +
                "</tr>\n</table>\n");
    }
}
