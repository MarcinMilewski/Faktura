package com.my.admin;

import com.my.item.Item;
import com.my.warehouse.Warehouse;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.operative.WarehouseOperative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Marcin on 11.01.2016.
 */
@Controller
@RequestMapping(value = "/admin/warehouse")
public class AdminWarehouseController {

    private static final String WAREHOUSE_VIEW_NAME = "/admin/warehouse/addWarehouse";
    private static final String SHOW_WAREHOUSE_VIEW_NAME = "/admin/warehouse/showWarehouses";

    @Autowired
    WarehouseRepository warehouseRepository;

    @RequestMapping
    public String items() {
        return WAREHOUSE_VIEW_NAME;
    }

    @RequestMapping(params={"name"}, method = RequestMethod.POST)
    public String addWarehouse(final HttpServletRequest req) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(req.getParameter("name"));
        warehouseRepository.save(warehouse);
        return "redirect:/admin/warehouse";
    }

    @RequestMapping(value="all")
    public ModelAndView viewWarehouses() {
        ModelAndView mv = new ModelAndView(SHOW_WAREHOUSE_VIEW_NAME);
        mv.addObject("warehouses",warehouseRepository.findAll());
        return mv;
    }

    @RequestMapping(value="remove", params={"id"})
    public String deleteWarehouse(final HttpServletRequest req) {
        warehouseRepository.delete(warehouseRepository.findOne(Long.valueOf(req.getParameter("id"))));
        return "redirect:/admin/warehouse/all";
    }

}
