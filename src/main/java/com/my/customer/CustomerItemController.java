package com.my.customer;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.item.Item;
import com.my.item.ItemInterface;
import com.my.item.decorator.OccasionalDiscount;
import com.my.item.decorator.RegularClientDiscount;
import com.my.item.dto.ItemDto;
import com.my.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 11.01.2016.
 */
@Controller
@RequestMapping(value = "user/items")
@SessionAttributes({"cart"})
public class CustomerItemController {

    private static final String SHOW_ITEM_VIEW_NAME = "/user/item/showItems";
    private static final String ITEM_DETAILS = "/user/item/details";

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserServiceFacade userServiceFacade;

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
        ItemInterface ii;
        Item item = itemRepository.findOne(id);
        if(account.getRegular()){
            ii = new RegularClientDiscount(item);
            item.setPrice(ii.getPrice());
        }
        if(!model.containsAttribute("cart")) {
            model.addAttribute("cart", new ArrayList<ItemDto>());
        }
        model.addAttribute("itemDto",new ItemDto());
        model.addAttribute("item", item);
        return ITEM_DETAILS;
    }

    @RequestMapping(value ="addToCart", method = RequestMethod.POST)
    public String addToShoppingCart(@ModelAttribute ItemDto product,
                                          @ModelAttribute("cart") List<ItemDto> cart) {
        cart.add(product);
        return SHOW_ITEM_VIEW_NAME;
    }


}

