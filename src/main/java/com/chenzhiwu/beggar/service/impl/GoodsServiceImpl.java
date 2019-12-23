package com.chenzhiwu.beggar.service.impl;

import com.alibaba.fastjson.JSON;
import com.chenzhiwu.beggar.common.data.PageSort;
import com.chenzhiwu.beggar.dao.GoodsDao;
import com.chenzhiwu.beggar.dao.MiaoshaGoodsDao;
import com.chenzhiwu.beggar.esdao.GoodsEsDao;
import com.chenzhiwu.beggar.pojo.*;
import com.chenzhiwu.beggar.service.GoodsService;
import com.chenzhiwu.beggar.service.OrderService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private GoodsEsDao goodsEsDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaGoodsDao miaoshaGoodsDao;

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
        goodsDao.saveAndFlush(goods);
        GoodsEs goodsEs = new GoodsEs();
        goodsEs.setId(goods.getId());
        goodsEs.setGoodsName(goods.getGoodsName());
        goodsEs.setGoodsTitle(goods.getGoodsTitle());
        goodsEs.setDownshelfTime(goods.getDownshelfTime());
        goodsEs.setUpshelfTime(goods.getUpshelfTime());
        goodsEs.setStatus(goods.getStatus());
        if(goods.getStatus() == null)
            goodsEs.setStatus(0);
        System.out.println("goodsEs"+ JSON.toJSONString(goodsEs)) ;
        goodsEsDao.save(goodsEs);
        System.out.println("goods"+ JSON.toJSONString(goods)) ;
        return;
    }


    public void deleteGoods(Long id) {
        goodsDao.deleteById(id);
        goodsEsDao.deleteById(id);
    }

    @Transactional
    public OrderInfo buyGoods(BeggarUser beggarUser, Goods goods) {
        //减库存
        goodsDao.reduceStock(goods.getId());
        //生成订单
        return orderService.createOrder(beggarUser, goods);
    }

    public List<Goods> fuzzyQueryGoodsNamePage(String goodsName, Integer page, Integer pageSize) {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("goodsName", goodsName));
        //排序id
        queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));

        // 分页：
        queryBuilder.withPageable(PageRequest.of(page,pageSize));

        // 搜索，获取结果id
        List<Long> idList = new ArrayList<>();
        Page<GoodsEs> items = goodsEsDao.search(queryBuilder.build());
        System.out.println("page:"+page);
        System.out.println("goodsName:"+goodsName);
        System.out.println("pageSize:"+pageSize);
        System.out.println("items.getTotalElements:"+items.getTotalElements());
        for (GoodsEs item : items) {
            idList.add(item.getId());
        }
        if(idList.isEmpty())
            return null;
        return goodsDao.batchGetGoodsByIdList(idList);
    }

    public void updateShelfTime(Long id, Date upshelfTime, Date downshelfTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsDao.updateShelfTime(id, formatter.format(upshelfTime), formatter.format(downshelfTime));
        return;
    }

    public List<Goods> getMsGoodsList(){
        return goodsDao.getMsGoodsList();
    }

    public MiaoshaGoods getMsInfoByGoodId(Long goodId) {
        return miaoshaGoodsDao.getMsInfoByGoodId(goodId);
    }

    public void saveMsGoods(MiaoshaGoods miaoshaGoods) {
        Goods goods = goodsDao.getGoodsById(miaoshaGoods.getGoodsId());
        goods.setIsMs(1);
        goods.setMsGoodsPrice(miaoshaGoods.getMiaoshaPrice());
        goodsDao.save(goods);
        miaoshaGoodsDao.saveAndFlush(miaoshaGoods);
        return;
    }

    public void delMsInfoByGoodId(Long goodId) {
        Goods goods = goodsDao.getGoodsById(goodId);
        goods.setIsMs(0);
        goodsDao.save(goods);
        miaoshaGoodsDao.delMsInfoByGoodId(goodId);
        return;
    }


}
