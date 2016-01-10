package com.my.item;

import com.my.repository.AbstractRepository;
import com.my.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Marcin on 09.01.2016.
 */
@Controller
public class ItemController {

    private static final String ITEM_VIEW_NAME = "items/addItem";

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value = "items")
    public String items(Model model) {
        model.addAttribute(new ItemForm());
        return ITEM_VIEW_NAME;
    }

    @RequestMapping(value = "items", method = RequestMethod.POST)
    public String addItem(@ModelAttribute ItemForm itemForm){
        Item item = itemForm.createItem();
        itemRepository.create(itemForm.createItem());
        return "redirect:/items";
    }

    @RequestMapping(value="items/all")
    public ModelAndView renderTable() {
        ModelAndView mv = new ModelAndView("items/showItems");
        mv.addObject("items",itemRepository.findAll());
        return mv;
    }
}
