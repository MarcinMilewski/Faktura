package com.my.customer;

import com.my.account.Account;
import com.my.account.UserService;
import com.my.order.OrderSummary;
import com.my.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by marcin on 10.01.16.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<OrderSummary> getUserOrders() {
        Account logged = userService.getLoggedUser();
        return orderService.getRepository().findByCustomerId(logged.getId());
    }
}
