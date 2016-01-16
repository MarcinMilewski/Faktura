package com.my.admin;

import com.my.item.Item;
import com.my.item.ItemInterface;
import com.my.item.decorator.OccasionalDiscount;
import com.my.item.form.ItemForm;
import com.my.item.repository.ItemRepository;
import com.my.warehouse.Warehouse;
import com.my.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Marcin on 10.01.2016.
 */
@Controller
@RequestMapping("/admin/items")
public class AdminItemController {
    private static final String ITEM_VIEW_NAME = "/admin/item/addItem";
    private static final String SHOW_ITEM_VIEW_NAME = "/admin/item/showItems";

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    WarehouseRepository warehouseRepository;


    @RequestMapping
    public ModelAndView items(Model model) {
        ModelAndView mv = new ModelAndView(ITEM_VIEW_NAME);
        ArrayList<Warehouse> warehouseList = new ArrayList<Warehouse>();
        Iterable<Warehouse> warehouseIterable = warehouseRepository.findAll();
        for(Warehouse w : warehouseIterable){
            warehouseList.add(w);
        }
        mv.addObject("warehouse",warehouseList);
        model.addAttribute( new ItemForm());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addItem(@ModelAttribute ItemForm itemForm, @RequestParam("vat") Integer vat){
        Warehouse warehouse = warehouseRepository.findOne(itemForm.getWarehouseId());

        ItemInterface ii;
        Item item = new Item();
        item.setName(itemForm.getName());
        item.setPrice(itemForm.getPrice());
        item.setAmount(itemForm.getAmount());
        item.setWarehouseAmount(itemForm.getAmount());
        item.setWarehouse(warehouse);
        item.setVat(vat);

        if(itemForm.getOccasional()){
            ii = new OccasionalDiscount(item);
            item.setPrice(ii.getPrice());
        }

        itemRepository.save(item);
        return "redirect:/admin/items";
    }

    @RequestMapping(value="all")
    public ModelAndView viewItems() {
        ModelAndView mv = new ModelAndView(SHOW_ITEM_VIEW_NAME);
        mv.addObject("items",itemRepository.findAll());
        return mv;
    }

    @RequestMapping(value="remove", params={"id"})
    public String deleteItem(final HttpServletRequest req) {
        itemRepository.delete(itemRepository.findOne(Long.valueOf(req.getParameter("id"))));
        return "redirect:/admin/items/all";
    }
}
