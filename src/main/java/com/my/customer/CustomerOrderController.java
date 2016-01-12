package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.item.cart.ItemCart;
import com.my.item.repository.ItemRepository;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;
import com.my.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

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
            List<OrderComponent> orders = orderRepository.findByCustomerId(account.getId());
            model.addAttribute("orders", orders);
        }

        return "user/order/showAll";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        if(account != null){
            OrderComponent order = orderRepository.findOne(id);
            model.addAttribute("order",order);
        }

        return "/user/order/details";

    }
}
