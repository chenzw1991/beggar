package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.Goods;
import org.springframework.data.domain.Page;

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
}
