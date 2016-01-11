package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.order.OrderComponent;
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
    private UserServiceFacade userServiceFacade;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<OrderComponent> getUserOrders() {
        Account logged = userServiceFacade.getLoggedUser();
        return orderService.getRepository().findByCustomerId(logged.getId());
    }

    @RequestMapping(value = "/order/pay", method = RequestMethod.GET)
    public List<OrderComponent> pay() {
        Account logged = userServiceFacade.getLoggedUser();
        return orderService.getRepository().findByCustomerId(logged.getId());
    }

}
