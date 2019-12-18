package com.chenzhiwu.beggar.controller;

import com.chenzhiwu.beggar.common.utils.ResultVoUtil;
import com.chenzhiwu.beggar.common.vo.ResultVo;
import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.OrderInfo;
import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.service.BeggarUserService;
import com.chenzhiwu.beggar.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private BeggarUserService beggarUserService;

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
    public String getGoodsDetail(Model model,HttpServletRequest request, @PathVariable("id")Long id){
        //得到session对象
        HttpSession session = request.getSession(false);
        if(session==null){
            //没有登录成功，跳转到登录页面
            return "login";
        }
        //取出会话数据
        Long userMobile = (Long)session.getAttribute("userMobile");
        if(userMobile==null){
            //没有登录成功，跳转到登录页面
            return "login";
        }


        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("userMobile", userMobile);
        model.addAttribute("goods", goods);
        return "goods_detail";

    }

    @RequestMapping("/buy")
    public String toList(Model model, HttpServletRequest request, @RequestParam("goodsId") Long goodsId) {
        //得到session对象
        HttpSession session = request.getSession(false);
        if(session==null){
            //没有登录成功，跳转到登录页面
            return "login";
        }
        //取出会话数据
        Long userMobile = (Long)session.getAttribute("userMobile");
        if(userMobile==null){
            //没有登录成功，跳转到登录页面
            return "login";
        }
        BeggarUser beggarUser = beggarUserService.getUserById(userMobile);
        //如果用户为空，则返回至登录页面
        if(beggarUser==null){
            return "login";
        }
        Goods goods=goodsService.getGoodsById(goodsId);
        //判断商品库存，库存大于0，才进行操作，多线程下会出错
        int  stockcount=goods.getGoodsStock();
        if(stockcount<=0) {//失败			库存至临界值1的时候，此时刚好来了加入10个线程，那么库存就会-10
            model.addAttribute("errorMessage", CodeMsg.MIAOSHA_OVER_ERROR);
            return "buy_fail";
        }

        OrderInfo orderinfo = goodsService.buyGoods(beggarUser, goods);
        model.addAttribute("user", beggarUser);
        model.addAttribute("orderinfo", orderinfo);
        model.addAttribute("goods", goods);
        return "order_detail";
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

    /**
     * 编辑类型
     * @description:
     * @author: IGG cmq
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVo GoodsSave( @RequestBody Goods goods) {
        System.out.println(goods);
        goodsService.saveGoods(goods);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    @PostMapping("/del")
    @ResponseBody
    public ResultVo typeDelete(Long id) {
        goodsService.deleteGoods(id);
        return ResultVoUtil.SAVE_SUCCESS;
    }
}
