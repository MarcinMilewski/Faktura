package com.my.admin;

import com.my.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marcin on 10.01.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ItemRepository itemRepository;

}
