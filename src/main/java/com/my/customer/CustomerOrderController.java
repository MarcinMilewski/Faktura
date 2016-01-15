package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.executor.IncorrectOperationException;
import com.my.executor.InvalidStateException;
import com.my.executor.OrderExecutor;
import com.my.invoice.Invoice;
import com.my.item.cart.ItemCart;
import com.my.item.repository.ItemRepository;
import com.my.order.OrderComponent;
import com.my.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    private OrderExecutor orderExecutor;



    @PostConstruct
    public void init() {
        orderExecutor = OrderExecutor.getInstance();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showOrders(Model model) {
        model.addAttribute("cart", itemCart.getCart());
        Account account = userServiceFacade.getLoggedUser();

        List<OrderComponent> orders = orderRepository.findByCustomerId(account.getId());
        model.addAttribute("orders", orders);

        return "user/order/showAll";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
            OrderComponent order = orderRepository.findOne(id);
            model.addAttribute("order",order);
            orderExecutor.createInvoiceHTML(order);
        return "/user/order/details";

    }

    @RequestMapping(value = "/pay", method = RequestMethod.GET, params = {"id"})
    public String pay(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
            OrderComponent order = orderRepository.findOne(id);
        try {
            orderExecutor.pay(order);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        } catch (IncorrectOperationException e) {
            e.printStackTrace();
        }
        return "redirect:/user/order";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET, params = {"id"})
    public String cancel(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        OrderComponent order = orderRepository.findOne(id);
        try {
            orderExecutor.cancel(order);
        } catch (InvalidStateException e) {
            e.printStackTrace();
        } catch (IncorrectOperationException e) {
            e.printStackTrace();
        }

        return "redirect:/user/order";
    }


    @RequestMapping(value = "/invoice", method = RequestMethod.GET, params = {"id"})
    public void invoice(HttpServletResponse response, @RequestParam("id") Long id) throws IOException {

        Account account = userServiceFacade.getLoggedUser();
        OrderComponent order = orderRepository.findOne(id);
        orderExecutor.createInvoiceHTML(order);
        response.setContentType("text/html");
        response.getWriter().print(orderExecutor.getInvoice());
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET, params = {"id"})
    public void saveInvoice(HttpServletResponse response, @RequestParam("id") Long id)
            throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=invoice.txt");
        OrderComponent order = orderRepository.findOne(id);
        orderExecutor.createInvoiceTxt(order);
        String str = orderExecutor.getInvoice();
        ServletOutputStream out = response.getOutputStream();
        out.print(str);
        out.flush();
        out.close();
    }
}
