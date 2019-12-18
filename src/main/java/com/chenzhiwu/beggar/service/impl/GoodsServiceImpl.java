package com.chenzhiwu.beggar.service.impl;

import com.alibaba.fastjson.JSON;
import com.chenzhiwu.beggar.common.data.PageSort;
import com.chenzhiwu.beggar.dao.GoodsDao;
import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.OrderInfo;
import com.chenzhiwu.beggar.service.GoodsService;
import com.chenzhiwu.beggar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/13-15 : 02
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderService orderService;

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

    public Page<Goods> getPageList(){
        PageRequest page = PageSort.pageRequest();
        Specification<Goods> spec = new Specification<Goods>() {
            private static final long serialVersionUID = 1L;

            @Override
            public  Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();// 查询条件
//                Date date = new Date();
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("downshelfTime").as(Date.class), date));
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("upshelfTime").as(Date.class), date));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };
        Page<Goods> list = goodsDao.findAll(spec, page);

        return list;

    }

    public void saveGoods(Goods goods) {
        goodsDao.save(goods);
        System.out.println("goods"+ JSON.toJSONString(goods)) ;
        return;
    }


    public void deleteGoods(Long id) {
        goodsDao.deleteById(id);
    }

    @Transactional
    public OrderInfo buyGoods(BeggarUser beggarUser, Goods goods) {
        //减库存
        goodsDao.reduceStock(goods.getId());
        //生成订单
        return orderService.createOrder(beggarUser, goods);
    }
}
