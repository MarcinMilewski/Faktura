package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.item.cart.ItemCart;
import com.my.item.repository.ItemRepository;
import com.my.order.OrderSummary;
import com.my.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by marcin on 11.01.16.
 */
@Controller
@RequestMapping(value = "user/order")
public class CustomerOrderController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserServiceFacade userServiceFacade;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemCart itemCart;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showOrders(Model model) {
        model.addAttribute("cart", itemCart.getCart());
        Account account = userServiceFacade.getLoggedUser();
        if (account != null) {
            List<OrderSummary> orders = orderRepository.findByCustomerId(account.getId());
            model.addAttribute("orders");
        }
        return "user/order/showAll";
    }
}
