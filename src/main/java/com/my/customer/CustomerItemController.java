package com.my.customer;

import com.my.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Marcin on 11.01.2016.
 */
@Controller
@RequestMapping(value = "user/items")
public class CustomerItemController {

    private static final String SHOW_ITEM_VIEW_NAME = "/user/item/showItems";

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping
    public ModelAndView viewItems() {
        ModelAndView mv = new ModelAndView(SHOW_ITEM_VIEW_NAME);
        mv.addObject("items",itemRepository.findAll());
        return mv;
    }

}

