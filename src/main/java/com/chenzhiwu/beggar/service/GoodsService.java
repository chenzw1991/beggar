package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.OrderInfo;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/13-15 : 02
 */
public interface GoodsService {
    public List<Goods> getGoodsPage(Integer skip, Integer take);

    public Goods getGoodsById(Long id);

    /**
     * 获取分页产品列表数据
     * @return 返回分页数据
     */
    public Page<Goods> getPageList();

    public void saveGoods(Goods goods);

    public void deleteGoods(Long id);
    //购买商品
    public OrderInfo buyGoods(BeggarUser beggarUser, Goods goods);

    public List<Goods> fuzzyQueryGoodsNamePage(String goodsName, Integer page, Integer pageSize);

    public void updateShelfTime(Long id, Date upshelfTime, Date downshelfTime);
}
