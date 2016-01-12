package com.my.admin;

import com.my.account.Account;
import com.my.account.UserServiceFacade;
import com.my.order.repository.OrderRepository;
import com.my.warehouse.Warehouse;
import com.my.warehouse.WarehouseRepository;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Marcin on 12.01.2016.
 */
@Controller
@RequestMapping(value = "/admin/operative")
public class AdminOperativeController {

    private static final String SHOW_OPERATIVE_VIEW_NAME = "/admin/operative/showOperatives";
    private static final String OPERATIVE_DETAILS_VIEW_NAME = "/admin/operative/details";
    private static final String OPERATIVE_TO_WAREHOUSE_VIEW_NAME = "/admin/operative/assign";

    @Autowired
    WarehouseOperativeRepository warehouseOperativeRepository;
    @Autowired
    UserServiceFacade userServiceFacade;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping
    public ModelAndView viewWarehouses() {
        ModelAndView mv = new ModelAndView(SHOW_OPERATIVE_VIEW_NAME);
        mv.addObject("operatives",warehouseOperativeRepository.findAll());
        return mv;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"id"})
    public String showDetails(Model model, @RequestParam("id") Long id) {
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_ADMIN")){
            WarehouseOperative opeartive = warehouseOperativeRepository.findOne(id);
            Warehouse warehouse = opeartive.getWarehouse();
            if(warehouse != null){
                model.addAttribute("warehouse",warehouse);
                return OPERATIVE_DETAILS_VIEW_NAME;
            }
            else {
                Iterable<Warehouse> warehouses = warehouseRepository.findAll();
                model.addAttribute("operative",opeartive);
                model.addAttribute("warehouses",warehouses);
                return OPERATIVE_TO_WAREHOUSE_VIEW_NAME;
            }

        }
        return "/";
    }

    @RequestMapping(value = "assign", params={"id","warehouse"}, method = RequestMethod.POST)
    public String assign(Model model, final HttpServletRequest req){
        Long oId = Long.valueOf(req.getParameter("id"));
        Long wId = Long.valueOf(req.getParameter("warehouse"));
        Account account = userServiceFacade.getLoggedUser();
        if(account != null && account.getRole().equals("ROLE_ADMIN")){
            Warehouse warehouse = warehouseRepository.findOne(wId);
            WarehouseOperative operative = warehouseOperativeRepository.findOne(oId);
            List<WarehouseOperative> list = warehouse.getWarehouseOperatives();
            operative.setWarehouse(warehouse);
            list.add(operative);
            warehouseRepository.setOperatives(list,wId);
            //warehouseOperativeRepository.setWarehouse(warehouse,oId);
            //warehouseOperativeRepository.setName("zmiana",oId);
            //Warehouse warehouse = warehouseRepository.findOne(wId);
            //WarehouseOperative operative = warehouseOperativeRepository.findOne(oId);
            //warehouseOperativeRepository.delete(oId);
            //operative.setId(null);
            //operative.setWarehouse(warehouse);
            //warehouseOperativeRepository.save(operative);
            //warehouseOperativeRepository.setWarehouse(wId,oId);
            //List<WarehouseOperative> operativeList = warehouse.getWarehouseOperatives();
            //operativeList.add(operative);
            //warehouse.setWarehouseOperatives(operativeList);
            //operative.setWarehouse(warehouse);
            //operative.setId(null);
            //warehouseOperativeRepository.delete(wId);
            //warehouseOperativeRepository.findOne(oId).setWarehouse(warehouse);
            //warehouseOperativeRepository.setWarehouse(wId,oId);
            return SHOW_OPERATIVE_VIEW_NAME;
        }
        return "/";
    }

}


