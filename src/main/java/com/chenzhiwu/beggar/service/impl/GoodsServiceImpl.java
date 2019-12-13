package com.chenzhiwu.beggar.service.impl;

import com.chenzhiwu.beggar.dao.GoodsDao;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/13-15 : 02
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Transactional
//    @CachePut(value = "redisCache", key = "'goodspage_'+#skip+'_'+#take")
    public List<Goods> getGoodsPage(Integer skip, Integer take){
//        Date nowTime = new Date();
        Pageable pageable = PageRequest.of(skip , take);
        return goodsDao.getGoodsPage(pageable);
    }


    public Goods getGoodsById(Long id){
        return goodsDao.getGoodsById(id);
    }
}
