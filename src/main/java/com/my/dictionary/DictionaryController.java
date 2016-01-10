package com.my.dictionary;

import com.google.common.collect.Lists;
import com.my.order.service.OrderService;
import com.my.warehouse.Warehouse;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by marcin on 10.01.16.
 */
@RestController
@RequestMapping(value = "/dictionaries")
public class DictionaryController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseOperativeRepository warehouseOperativeRepository;

    @RequestMapping(value = "/warehouses", method = RequestMethod.GET)
    public List<Warehouse> getUserOrders() {
        return Lists.newArrayList(warehouseRepository.findAll());
    }

    @RequestMapping(value = "/operatives", method = RequestMethod.GET)
    public List<WarehouseOperative> getWarehouseOperatives() {
        return Lists.newArrayList(warehouseOperativeRepository.findAll());
    }

}
