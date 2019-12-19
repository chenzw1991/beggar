package com.chenzhiwu.beggar.service.impl;

import com.chenzhiwu.beggar.common.data.PageSort;
import com.chenzhiwu.beggar.dao.OrderDao;
import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.OrderInfo;
import com.chenzhiwu.beggar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/18-16 : 10
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    public void saveOrderInfo(OrderInfo orderInfo){
        orderDao.save(orderInfo);
        return;
    }

    public Page<OrderInfo> getOrderInfoByUinPageList(Long userId){
        PageRequest page = PageSort.pageRequest();
        Specification<OrderInfo> spec = new Specification<OrderInfo>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();// 查询条件

                predicates.add(criteriaBuilder.equal(root.get("user_id").as(Long.class), userId));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };
        Page<OrderInfo> list = orderDao.findAll(spec, page);

        return list;

    }

    /**
     * 生成订单,
     * @param beggarUser
     * @param goods
     * @return
     */
    public OrderInfo createOrder(BeggarUser beggarUser, Goods goods) {
        //1.生成order_info
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setDeliveryAddrId(0L);//long类型 private Long deliveryAddrId;   L
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        //价格
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        //订单状态  ---0-新建未支付  1-已支付  2-已发货  3-已收货
        orderInfo.setOrderStatus(0);
        //用户id
        orderInfo.setUserId(beggarUser.getId());
        orderDao.saveAndFlush(orderInfo);
        return orderInfo;
    }

    public Page<OrderInfo> getPageList() {
        PageRequest page = PageSort.pageRequest();
        Specification<OrderInfo> spec = new Specification<OrderInfo>() {
            private static final long serialVersionUID = 1L;

            @Override
            public  Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();// 查询条件
//                Date date = new Date();
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("downshelfTime").as(Date.class), date));
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("upshelfTime").as(Date.class), date));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        };
        Page<OrderInfo> list = orderDao.findAll(spec, page);

        return list;
    }
}
