package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.item.cart.ItemCart;
import com.my.item.repository.ItemRepository;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;
import com.my.order.repository.OrderRepository;
import com.my.order.state.OrderStatePaid;
import com.my.order.state.OrderStateType;
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
        if(account != null && account.getRole().equals("ROLE_USER")){
            OrderComponent order = orderRepository.findOne(id);
            model.addAttribute("order",order);
        }

        return "/user/order/details";

    }

    @RequestMapping(value = "/pay", method = RequestMethod.GET, params = {"id"})
    public String pay(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_USER")){
            OrderComponent order = orderRepository.findOne(id);
            /*tu nie jestem pewien czy kazdy item oznaczac jako paid, jak cos to dziala to sie odkomentuje
            Set<OrderComponent> orders = order.getChildren();
            for(OrderComponent o : orders){
                o.setState(new OrderStatePaid());
            }
            */
            order.setState(new OrderStatePaid());
            orderRepository.save(order);
        }

        return "/user/order/showAll";

    }
}
