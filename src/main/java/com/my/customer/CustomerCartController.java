package com.my.customer;

import com.google.common.collect.Sets;
import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.executor.OrderExecutor;
import com.my.item.Item;
import com.my.item.cart.ItemCart;
import com.my.item.repository.ItemRepository;
import com.my.logger.Log;
import com.my.order.OrderComponent;
import com.my.order.OrderItem;
import com.my.order.OrderSummary;
import com.my.order.repository.OrderRepository;
import com.my.order.send.SendStrategyType;
import com.my.order.send.factory.SendStrategyFactory;
import com.my.order.send.repository.SendRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by marcin on 11.01.16.
 */
@Controller
@RequestMapping(value = "user/cart")
public class CustomerCartController {
    @Log
    Logger logger;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserServiceFacade userServiceFacade;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemCart itemCart;
    @Autowired
    private SendRepository sendRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showOrders(Model model) {
        model.addAttribute("cart", itemCart.getCart());
        Account account = userServiceFacade.getLoggedUser();
        if (account != null) {
            List<OrderComponent> orders = orderRepository.findByCustomerId(account.getId());
            model.addAttribute("orders");
        }
        return "/user/cart/show";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public String clear(){

        if(!itemCart.getCart().isEmpty()){
            itemCart.clearAndReturnItems();
            itemCart.clear();
        }

        return "redirect:/user/cart";
    }

    @RequestMapping(value = "/doOrder", method = RequestMethod.POST)
    public String doOrder(Model model, @RequestParam("type") String type) {
        Set<Item> items = new HashSet<>();
        itemCart.getCart().stream().forEach(itemDto -> items.add(itemRepository.findOne(itemDto.getId())));

        Map<Item, Integer> itemAmountMap = items.stream()
                .map(item ->
                new AbstractMap.SimpleImmutableEntry<>(
                        item , itemCart.getCart()
                        .stream()
                        .filter(itemDto -> item.getId().equals(itemDto.getId()))
                        .findFirst().get().getAmount()))
                        .collect(Collectors.toMap(Map.Entry::getKey ,Map.Entry::getValue));

        Set<OrderComponent> orderComponents = Sets.newHashSet();
        itemAmountMap.entrySet().stream().forEach(entry -> orderComponents.add(createOrderItem(entry)));

        OrderComponent orderSummary = createOrderSummary(orderComponents);
        if(type.equals("Economic")){
            orderSummary.setSendStrategy(SendStrategyFactory.getSendStrategy(SendStrategyType.ECONOMIC));
        }
        else if(type.equals("Express")){
            orderSummary.setSendStrategy(SendStrategyFactory.getSendStrategy(SendStrategyType.EXPRESS));
        }
        else if(type.equals("Personal")){
            orderSummary.setSendStrategy(SendStrategyFactory.getSendStrategy(SendStrategyType.PERSONAL));
        }
        OrderExecutor.getInstance().addNew(orderSummary);

        itemCart.clear();

        return "/user/cart/orderApproved";
    }

    private OrderSummary createOrderSummary(Set<OrderComponent> orderComponents) {
        Date date = new Date();
        Account purchaser = userServiceFacade.getLoggedUser();

        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setParent(null);
        orderSummary.setChildren(orderComponents);
        orderSummary.setPurchaseDate(new Date());
        orderSummary.setReceivedDate(null);
        orderSummary.setCustomer(purchaser);
        orderSummary.setSendDate(null);
        orderSummary.setPrice(calculateTotalOrderPrice(orderComponents));
        return orderSummary;
    }

    private BigDecimal calculateTotalOrderPrice(Set<OrderComponent> orderComponents) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderComponent orderComponent : orderComponents) {
            totalPrice = totalPrice.add(orderComponent.getPrice());
        }
        return totalPrice;
    }

    private OrderItem createOrderItem(Map.Entry<Item, Integer> entry) {
        OrderItem orderItem = new OrderItem();
        Item item = entry.getKey();
        Integer amount = entry.getValue();
        orderItem.setItem(item);
        orderItem.setAmount(amount);
        orderItem.setPrice(item.getPrice().multiply(new BigDecimal(amount)));
        return orderItem;
    }

}
