package com.my.order.controller;

import com.my.order.OrderComponent;
import com.my.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marcin on 09.01.16.
 */
@RestController
@RequestMapping("/order")
@Secured("ROLE_USER")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public OrderComponent getEmployeeInJSON(@PathVariable String name) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }

}
