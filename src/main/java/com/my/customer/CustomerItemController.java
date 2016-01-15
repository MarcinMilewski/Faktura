package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.item.Item;
import com.my.item.ItemInterface;
import com.my.item.cart.ItemCart;
import com.my.item.decorator.RegularClientDiscount;
import com.my.item.dto.ItemDto;
import com.my.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Marcin on 11.01.2016.
 */
@Controller
@RequestMapping(value = "user/items")
public class CustomerItemController {

    private static final String SHOW_ITEM_VIEW_NAME = "/user/item/showItems";
    private static final String ITEM_DETAILS = "/user/item/details";

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserServiceFacade userServiceFacade;
    @Autowired
    private ItemCart cart;

    @RequestMapping(method = RequestMethod.GET)
    public String viewItems(Model model) {
        Account account = userServiceFacade.getLoggedUser();
        ItemInterface ii;
        Iterable<Item> items = itemRepository.findAll();

        if(account.getRegular()){
            for(Item i : items){
                ii = new RegularClientDiscount(i);
                i.setPrice(ii.getPrice());
            }
        }

        model.addAttribute("items", items);
        return SHOW_ITEM_VIEW_NAME;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        ItemInterface itemInterface;
        Item item = itemRepository.findOne(id);
        if(account.getRegular()){
            itemInterface = new RegularClientDiscount(item);
            item.setPrice(itemInterface.getPrice());
        }
        model.addAttribute("itemDto",new ItemDto());
        model.addAttribute("item", item);
        return ITEM_DETAILS;
    }

    @RequestMapping(value ="addToCart", method = RequestMethod.POST)
    public String addToShoppingCart(@ModelAttribute ItemDto itemDto) {
        Item item = itemRepository.findOne(itemDto.getId());
        item.setAmount(item.getAmount() - itemDto.getAmount());
        itemRepository.save(item);
        cart.addToCart(itemDto);
        return "redirect:/user/items";
    }



}

