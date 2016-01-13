package com.my.operative;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.executor.IncorrectOperationException;
import com.my.executor.InvalidStateException;
import com.my.executor.OrderExecutor;
import com.my.logger.Log;
import com.my.order.OrderComponent;
import com.my.order.repository.OrderRepository;
import com.my.order.state.OrderStateType;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by Marcin on 12.01.2016.
 */
@Controller
@RequestMapping("/operative/orders")
public class OperativeOrdersController {

    @Log
    Logger logger;
    @Autowired
    UserServiceFacade userServiceFacade;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WarehouseOperativeRepository warehouseOperativeRepository;

    private OrderExecutor orderExecutor;

    @PostConstruct
    public void initialize() {
        orderExecutor = OrderExecutor.getInstance();
    }

    @RequestMapping
    public String showOrders(Model model) {
        Account account = userServiceFacade.getLoggedUser();
            Long id = account.getWarehouseOperative().getId();
            Set<OrderComponent> orders = account.getWarehouseOperative().getOrderItems();
            model.addAttribute("orders", orders);


        return "operative/order/orderList";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
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
        return "/operative/order/details";

    }

    @RequestMapping(value = "/completed", method = RequestMethod.GET, params = {"id"})
    public String completed(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
            OrderComponent order = orderRepository.findOne(id).getParent();
        try {
            //TODO notify observer
            orderExecutor.send(order);
            //logger.debug("orderComponent completed " + id);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        } catch (IncorrectOperationException e) {
            e.printStackTrace();
        }
        return "/user/order/showAll";

    }
}
