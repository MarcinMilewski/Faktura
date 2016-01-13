package com.my.operative;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.order.OrderComponent;
import com.my.order.OrderSummary;
import com.my.order.repository.OrderRepository;
import com.my.order.state.OrderState;
import com.my.order.state.OrderStateCompleted;
import com.my.order.state.OrderStateSend;
import com.my.order.state.OrderStateType;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    @Autowired
    WarehouseOperativeRepository warehouseOperativeRepository;

    @RequestMapping
    public String showOrders(Model model) {
        Account account = userServiceFacade.getLoggedUser();

        if (account != null && account.getRole().equals("ROLE_OPERATIVE")) {
            Long id = account.getWarehouseOperative().getId();
            Set<OrderComponent> orders = account.getWarehouseOperative().getOrderItems();
            model.addAttribute("orders", orders);
        }

        return "operative/order/orderList";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_OPERATIVE")){
            Boolean paid = false, completed = false, send = false;
            WarehouseOperative operative = account.getWarehouseOperative();
            OrderComponent order = orderRepository.findOne(id);
            if(order.getParent().getState().getOrderStateType().equals(OrderStateType.PAID) && !order.getState().getOrderStateType().equals(OrderStateType.COMPLETED)){
                paid = true;
            }
            if(order.getParent().getState().getOrderStateType().equals(OrderStateType.COMPLETED)){
                completed = true;
            }
            if(order.getState().getOrderStateType().equals(OrderStateType.SEND)){
                send = true;
            }
            model.addAttribute("paid",paid);
            model.addAttribute("completed",completed);
            model.addAttribute("send",send);
            model.addAttribute("order",order);
            //order.getParent().getState()
        }
        return "/operative/order/details";

    }

    @RequestMapping(value = "/completed", method = RequestMethod.GET, params = {"id"})
    public String completed(Model model, @RequestParam("id") Long id) {
        Boolean completed = true;
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_OPERATIVE")){
            OrderComponent order = orderRepository.findOne(id);
            Set<OrderComponent> orders = order.getParent().getChildren();
            Iterator<OrderComponent> i = orders.iterator();

            order.setState(new OrderStateCompleted());
            orderRepository.save(order);

            while(i.hasNext()){
                if(!i.next().getState().getOrderStateType().equals(OrderStateType.COMPLETED)){
                    completed = false;
                }
            }
            if(completed){
                OrderComponent summary = order.getParent();
                summary.setState(new OrderStateCompleted());
                orderRepository.save(summary);
            }

        }


        return "/user/order/showAll";

    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, params = {"id"})
    public String send(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_OPERATIVE")){
            OrderComponent order = orderRepository.findOne(id).getParent();
            order.setState(new OrderStateSend());
            orderRepository.save(order);
        }

        return "/user/order/showAll";

    }
}
