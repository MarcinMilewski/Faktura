package com.my.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
        itemRepository.save(itemForm.createItem());
        return "redirect:/items";
    }

    @RequestMapping(value="items/all")
    public ModelAndView renderTable() {
        ModelAndView mv = new ModelAndView("items/showItems");
        mv.addObject("items",itemRepository.findAll());
        return mv;
    }

    @RequestMapping(value="items/remove", params={"id"})
    public String deleteItem(final HttpServletRequest req) {
        Item item = new Item();
        itemRepository.delete(itemRepository.findOne(Long.valueOf(req.getParameter("id"))));
        return "redirect:/items/all";
    }
}
