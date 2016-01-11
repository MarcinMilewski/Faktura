package com.my.customer;

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

    @RequestMapping(method = RequestMethod.GET)
    public String viewItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return SHOW_ITEM_VIEW_NAME;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        if(!model.containsAttribute("cart")) {
            model.addAttribute("cart", new ArrayList<ItemDto>());
        }
        model.addAttribute("itemDto",new ItemDto());
        model.addAttribute("item", itemRepository.findOne(id));
        return ITEM_DETAILS;
    }

    @RequestMapping(value ="addToCart", method = RequestMethod.POST)
    public String addToShoppingCart(@ModelAttribute ItemDto product,
                                          @ModelAttribute("cart") List<ItemDto> cart) {
        cart.add(product);
        return SHOW_ITEM_VIEW_NAME;
    }


}

