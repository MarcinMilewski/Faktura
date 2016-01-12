package com.my.operative;

/**
 * Created by marcin on 10.01.16.
 */

import com.my.account.UserServiceFacade;
import com.my.warehouse.Warehouse;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/operative")
public class OperativeController {

    private static final String SHOW_ITEM_VIEW_NAME = "/operative/item/showItems";

    @Autowired
    UserServiceFacade userServiceFacade;
    @Autowired
    WarehouseOperativeRepository warehouseOperativeRepository;

    @RequestMapping(value = "warehouse")
    public ModelAndView viewItems() {
        ModelAndView mv = new ModelAndView(SHOW_ITEM_VIEW_NAME);
        WarehouseOperative warehouseOperative = userServiceFacade.getLoggedUser().getWarehouseOperative();
        Warehouse warehouse = warehouseOperative.getWarehouse();
        mv.addObject("warehouse",warehouse.getName());
        mv.addObject("items",warehouse.getItems());
        return mv;
    }
}
