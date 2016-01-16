package com.my.admin;

import com.my.account.Account;
import com.my.account.AccountRepository;
import com.my.warehouse.operative.WarehouseOperative;
import com.my.warehouse.operative.WarehouseOperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 16.01.2016.
 */
@Controller
@RequestMapping(value = "/admin/users")
public class AdminUserController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WarehouseOperativeRepository warehouseOperativeRepository;

    @RequestMapping
    public ModelAndView viewWarehouses() {
        ModelAndView mv = new ModelAndView("/admin/user/show");
        List users = new ArrayList<Account>();
        for(Account a : accountRepository.findAll()){
            if(a.getRole().equals("ROLE_USER")){
                users.add(a);
            }
        }
        mv.addObject("users",users);
        return mv;
    }

    @RequestMapping(value = "/make", method = RequestMethod.GET, params = {"email"})
    public String makeRegular(Model model, @RequestParam("email") String email){
        Account user = accountRepository.findByEmail(email);
        user.setRegular(true);
        accountRepository.edit(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/unmake", method = RequestMethod.GET, params = {"email"})
    public String unmakeRegular(Model model, @RequestParam("email") String email){
        Account user = accountRepository.findByEmail(email);
        user.setRegular(false);
        accountRepository.edit(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/operator", method = RequestMethod.GET, params = {"email"})
    public String makeOperative(Model model, @RequestParam("email") String email){
        Account user = accountRepository.findByEmail(email);
        user.setRole("ROLE_OPERATIVE");
        accountRepository.edit(user);
        user = accountRepository.findByEmail(email);
        WarehouseOperative operator = new WarehouseOperative();
        operator.setFirstName(user.getFirstName());
        operator.setLastName(user.getLastName());
        operator.setAccount(user);
        warehouseOperativeRepository.save(operator);
        return "redirect:/admin/users";
    }
}
