package com.my.operative;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.order.OrderComponent;
import com.my.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Marcin on 12.01.2016.
 */
@Controller
@RequestMapping("/operative/orders")
public class OperativeOrdersController {

    @Autowired
    UserServiceFacade userServiceFacade;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping
    public String showOrders(Model model) {
        Account account = userServiceFacade.getLoggedUser();

        if (account != null && account.getRole().equals("ROLE_OPERATIVE")) {
            //List<OrderComponent> orders = orderRepository.findByCustomerId(0);
            //model.addAttribute("orders", orders);
        }

        return "user/order/showAll";
    }
}
