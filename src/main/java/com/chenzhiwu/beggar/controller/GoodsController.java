package com.chenzhiwu.beggar.controller;

import com.chenzhiwu.beggar.common.redis.GoodsKey;
import com.chenzhiwu.beggar.common.redis.RedisService;
import com.chenzhiwu.beggar.common.utils.ResultVoUtil;
import com.chenzhiwu.beggar.common.vo.ResultVo;
import com.chenzhiwu.beggar.pojo.*;
import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.service.BeggarUserService;
import com.chenzhiwu.beggar.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    private RedisService redisService;

    //注入渲染
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    private final Integer take = 10;

    @RequestMapping(value="/to_list/{pageId}")
    public String getGoodsPage(Model model, BeggarUser beggarUser, @PathVariable("pageId")Integer pageId){
        model.addAttribute("beggarUser", beggarUser);
//        Integer skipId = pageId * take;
//        //查询商品列表
//        Integer havenext = 1;//是否有下一页 0 否 1是
//        List<Goods> goodsList= goodsService.getGoodsPage(skipId, take);
//        if(goodsList.size() < take){
//            havenext = 0;
//        }
//        else {
//            List<Goods> tmpList= goodsService.getGoodsPage(skipId + take, take);
//            if(tmpList.size() == 0){
//                havenext = 0;
//            }
//        }
        Page<Goods> list = goodsService.getPageList(pageId);

        model.addAttribute("goodsList", list.getContent());
        model.addAttribute("indexPage", pageId);
        model.addAttribute("totalPage", list.getTotalPages());
        return "goods_list";
    }

    @RequestMapping(value="/to_detail/{id}", produces = "text/html")
    @ResponseBody
    public String getGoodsDetail(Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable("id")Long id){

        // 1.取缓存
        // public <T> T get(KeyPrefix prefix,String key,Class<T> data)
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+id, String.class);//不同商品页面不同的详情
        if (!StringUtils.isEmpty(html)) {
            System.out.println("get goods detail from redis");
            return html;
        }
        //缓存中没有，则将业务数据取出，放到缓存中去。
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods", goods);

        //手动渲染
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        // 将渲染好的html保存至缓存
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+id, html);
        }
        return html;//html是已经渲染好的html文件
//        return "goods_detail";

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
        Date date = new Date();
        if(goods.getDownshelfTime().before(date)) {
            model.addAttribute("errorMessage", CodeMsg.GOODS_DOWNSHELF);
            return "buy_fail";
        }

        if(goods.getUpshelfTime().after(date)) {
            model.addAttribute("errorMessage", CodeMsg.GOODS_UPSHELF);
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

        //设置上下架状态
        Date dateNow = new Date();
        for (Goods item : list) {
            //默认下架状态
            item.setStatus(0);
            //上架状态
            if(item.getUpshelfTime().before(dateNow)  && dateNow.before(item.getDownshelfTime()) ){
                item.setStatus(1);
            }
        }
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
        if(id != 0) {
            Goods goods = goodsService.getGoodsById(id);
            model.addAttribute("info", goods);
            return "admin_goods_edit";
        }//编辑
        Goods goods = new Goods();
        model.addAttribute("info", goods);
        return "admin_goods_edit";
    }

    /**
     * 编辑类型
     * @description:
     * @author: IGG
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVo GoodsSave( @RequestBody Goods goods) {
        System.out.println(goods);
        goodsService.saveGoods(goods);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    @GetMapping("/del/{id}")
    @ResponseBody
    public ResultVo goodsDelete(@PathVariable("id")Long id) {
        goodsService.deleteGoods(id);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 快速上下架
     * @description:商品上下架
     * @author: IGG
     * @return
     */
    @GetMapping("/shelf/{id}/{status}")
    @ResponseBody
    public ResultVo Goodshelf( @PathVariable("id") Long id, @PathVariable("status") Long status) {
        System.out.println("id="+id);
        System.out.println("status="+status);
        Goods goods = goodsService.getGoodsById(id);
        Date dateNow = new Date();
        if(status == 0) {
            goods.setDownshelfTime(dateNow);
        } else {
            goods.setUpshelfTime(dateNow);
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1); //增加一年
            Date dateF=curr.getTime();
            goods.setDownshelfTime(dateF);
        }
        //后续再考虑优化只更新上下架时间字段
//        goodsService.updateShelfTime(id, goods.getUpshelfTime(), goods.getDownshelfTime());
        goodsService.saveGoods(goods);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 是否秒杀
     * @description:商品上下架
     * @author: IGG
     * @return
     */
    @GetMapping("/ms/{id}/{status}")
    @ResponseBody
    public ResultVo GoodMs( @PathVariable("id") Long id, @PathVariable("status") Integer status) {
        Goods goods = goodsService.getGoodsById(id);
        if(goods.getMsGoodsPrice() <= 0) {
            return ResultVoUtil.error("请设置秒杀价格");
        }
        goods.setIsMs(status);
        goodsService.saveGoods(goods);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    @RequestMapping(value="/to_mslist", produces = "text/html")
    @ResponseBody
    public String getMsGoodsList(Model model, HttpServletRequest request, HttpServletResponse response){
        // 1.取缓存
        String html = redisService.get(GoodsKey.getMsGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            System.out.println("get ms goods list from redis");
            return html;
        }
        List<Goods> goodsList= goodsService.getMsGoodsList();
        model.addAttribute("goodsList", goodsList);
        //手动渲染
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_mslist", ctx);
        // 将渲染好的html保存至缓存
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getMsGoodsList, "", html);
        }
        return html;//html是已经渲染好的html文件
//        return "goods_mslist";
    }

    @GetMapping(value="/to_msdetail/{id}", produces = "text/html")
    @ResponseBody
    public String getMsGoodsDetail(Model model,HttpServletRequest request, HttpServletResponse response, @PathVariable("id")Long id){
        //得到session对象
        // 1.取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + id, String.class);
        if (!StringUtils.isEmpty(html)) {
            System.out.println("get ms goods detail from redis");
            return html;
        }

        Goods goods = goodsService.getGoodsById(id);
        MiaoshaGoods miaoshaGoods = goodsService.getMsInfoByGoodId(id);
        //既然是秒杀，还要传入秒杀开始时间，结束时间等信息
        long start=miaoshaGoods.getStartDate().getTime();
        long end=miaoshaGoods.getEndDate().getTime();
        long now=System.currentTimeMillis();
        //秒杀状态量
        int status=0;
        //开始时间倒计时
        int remailSeconds=0;
        //查看当前秒杀状态
        if(now<start) {//秒杀还未开始，--->倒计时
            status=0;
            remailSeconds=(int) ((start-now)/1000);  //毫秒转为秒
        }else if(now>end){ //秒杀已经结束
            status=2;
            remailSeconds=-1;  //毫秒转为秒
        }else {//秒杀正在进行
            status=1;
            remailSeconds=0;  //毫秒转为秒
        }

        model.addAttribute("status", status);
        model.addAttribute("remailSeconds", remailSeconds);
        model.addAttribute("goods", goods);
        //手动渲染
        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_msdetail", ctx);
        // 将渲染好的html保存至缓存
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getMsGoodsList, "", html);
        }
        return html;//html是已经渲染好的html文件
//        return "goods_msdetail";

    }

    /**
     * 跳转到秒杀编辑页面
     * @description:
     * @author: IGG
     */
    @GetMapping("/admin_msedit/{id}")
    public String goodsMsEdit(@PathVariable("id") Long id, Model model) {
        Date date = new Date();
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setId(id);
        miaoshaGoods.setGoodsId(id);
        miaoshaGoods.setStartDate(date);
        miaoshaGoods.setEndDate(date);
        model.addAttribute("info", miaoshaGoods);
        return "admin_msgoods_edit";
    }

    /**
     * 编辑类型
     * @description:
     * @author: IGG
     * @return
     */
    @PostMapping("/mssave")
    @ResponseBody
    public ResultVo goodsMsSave( @RequestBody MiaoshaGoods miaoshaGoods) {
        System.out.println(miaoshaGoods);
        goodsService.saveMsGoods(miaoshaGoods);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 取消秒杀
     * @description:
     * @author: IGG
     * @return
     */
    @GetMapping("/msdel/{goodsId}")
    @ResponseBody
    public ResultVo goodsMsDel(@PathVariable("goodsId") Long goodsId) {
        goodsService.delMsInfoByGoodId(goodsId);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    @PostMapping(value="/fuzzyQuery")
    public String fuzzyQueryGoodsNamePage(Model model, SearchGoodsReqVo searchGoodsReqVos){
        //查询商品列表
        Integer havenext = 1;//是否有下一页 0 否 1是
        List<Goods> goodsList= goodsService.fuzzyQueryGoodsNamePage(searchGoodsReqVos.getSearchName() , searchGoodsReqVos.getSearchPageid(), take);
        if(goodsList.size() < take){
            havenext = 0;
        }
        else {
            List<Goods> tmpList= goodsService.fuzzyQueryGoodsNamePage(searchGoodsReqVos.getSearchName() , searchGoodsReqVos.getSearchPageid() + 1, take);
            if(tmpList.size() == 0){
                havenext = 0;
            }
        }
        System.out.println("searchGoodsReqVos" + searchGoodsReqVos);
        System.out.println("goodssize:" + goodsList.size());
        System.out.println("take:" + take);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("pagid", searchGoodsReqVos.getSearchPageid());
        model.addAttribute("havenext", havenext);
        model.addAttribute("name", searchGoodsReqVos.getSearchName());
        return "fuzzyquery_goods_list";
    }

}
