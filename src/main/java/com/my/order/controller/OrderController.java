package com.my.order.controller;

import com.my.account.Account;
import com.my.account.AccountRepository;
import com.my.order.OrderSummary;
import com.my.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by marcin on 09.01.16.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<OrderSummary> getUserOrders() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account logged = accountRepository.findByEmail(principal.getUsername());
        return orderService.getRepository().findByCustomerId(logged.getId());
    }

    @RequestMapping(value = "/operator", method = RequestMethod.GET)
    public List<OrderSummary> getOperatorOrders() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account logged = accountRepository.findByEmail(principal.getUsername());
        return orderService.getRepository().findByCustomerId(logged.getId());
    }


}
