package com.my.invoice.builder;

import com.my.item.Item;
import com.my.order.OrderComponent;
import com.my.order.OrderSummary;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Marcin on 14.01.2016.
 */

public class InvoiceNew {

    private ArrayList<Item> items;
    private OrderSummary orderSummary;

    public InvoiceNew (OrderSummary order){
        orderSummary = order;

    }
}
