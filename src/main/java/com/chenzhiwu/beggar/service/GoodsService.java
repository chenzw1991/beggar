package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.Goods;

import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/13-15 : 02
 */
public interface GoodsService {
    public List<Goods> getGoodsPage(Integer skip, Integer take);

    public Goods getGoodsById(Long id);
}
