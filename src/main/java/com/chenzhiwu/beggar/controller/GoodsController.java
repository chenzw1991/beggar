package com.chenzhiwu.beggar.controller;

import com.alibaba.fastjson.JSONArray;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.User;
import com.chenzhiwu.beggar.service.GoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    private final Integer take = 50;

    @RequestMapping(value="/to_list/{pageId}")
    public String getGoodsPage(Model model, User user,
                               HttpServletRequest request, HttpServletResponse response, @PathVariable("pageId")Integer pageId){
        model.addAttribute("user", user);
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
    public String getGoodsDetail(Model model, User user,
                                 HttpServletRequest request, HttpServletResponse response, @PathVariable("id")Long id){
        model.addAttribute("user", user);
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods", goods);
        return "goods_detail";

    }

    @GetMapping("/admin_goodslist")
    public String typeList(Model model, FeedbackType fbType) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<FeedbackType> example = Example.of(fbType, matcher);
        Page<FeedbackType> list = feedbackService.getPageTypeList(example);
        for (FeedbackType feedbackType : list.getContent()) {
            JSONArray names = JSONArray.parseArray(feedbackType.getNames());
            for (Object object : names) {
                JSONObject item=(JSONObject) object;
                if ("zh_CN".equals(item.getString("lang"))){
                    feedbackType.setNames(item.getString("name"));//取中文
                    break;
                }
            }
        }
        model.addAttribute("list",list.getContent());
        model.addAttribute("page",list);//分页依赖
        return "/business/feedback/typelist" ;
    }
}
