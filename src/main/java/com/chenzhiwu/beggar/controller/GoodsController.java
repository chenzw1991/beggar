package com.chenzhiwu.beggar.controller;

import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/13-10 : 48
 */
@RequestMapping("/goods")
@Controller
@Slf4j
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    private final Integer take = 50;

    @RequestMapping(value="/to_list/{pageId}")
    public String getGoodsPage(Model model, BeggarUser beggarUser,
                               HttpServletRequest request, HttpServletResponse response, @PathVariable("pageId")Integer pageId){
        model.addAttribute("beggarUser", beggarUser);
        Integer skipId = pageId * take;
        //查询商品列表
        Integer havenext = 1;//是否有下一页 0 否 1是
        List<Goods> goodsList= goodsService.getGoodsPage(skipId, take);
        if(goodsList.size() < take){
            havenext = 0;
        }
        else {
            List<Goods> tmpList= goodsService.getGoodsPage(skipId + take, take);
            if(tmpList.size() == 0){
                havenext = 0;
            }
        }
        System.out.println("goodssize:" + goodsList.size());
        System.out.println("skipId:" + skipId);
        System.out.println("take:" + take);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("pagid", pageId);
        model.addAttribute("havenext", havenext);
        return "goods_list";
    }

    @RequestMapping(value="/to_detail/{id}")
    public String getGoodsDetail(Model model,HttpServletRequest request, HttpServletResponse response, @PathVariable("id")Long id){
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods", goods);
        return "goods_detail";

    }

    @GetMapping("/admin_goodslist")
    public String adminGoodsList(Model model) {
        Page<Goods> list = goodsService.getPageList();
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);//分页依赖
        System.out.println(list);
        return "admin_goods_list" ;
    }

    /**
     * 跳转到编辑页面
     * @description:
     * @author: IGG
     */
    @GetMapping("/admin_goodsedit/{id}")
    public String goodsEdit(@PathVariable("id") Long id, Model model) {
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("info", goods);
        return "admin_goods_edit";
    }
}
