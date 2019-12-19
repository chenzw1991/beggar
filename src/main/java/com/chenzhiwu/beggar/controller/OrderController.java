package com.chenzhiwu.beggar.controller;

import com.chenzhiwu.beggar.pojo.OrderInfo;
import com.chenzhiwu.beggar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:IGG
 * @date:2019/12/19-14 : 50
 */
@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    private final Integer take = 50;

    @GetMapping("/admin_orderlist")
    public String adminGoodsList(Model model) {
        Page<OrderInfo> list = orderService.getPageList();
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);//分页依赖
        System.out.println(list);
        return "admin_order_list" ;
    }
}
