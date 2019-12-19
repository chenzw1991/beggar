package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.pojo.Goods;
import com.chenzhiwu.beggar.pojo.OrderInfo;
import org.springframework.data.domain.Page;

/**
 * @author:IGG
 * @date:2019/12/18-16 : 06
 */
public interface OrderService {
    public void saveOrderInfo(OrderInfo orderInfo);

    public Page<OrderInfo> getOrderInfoByUinPageList(Long userId);

    public OrderInfo createOrder(BeggarUser beggarUser, Goods goods);

    /**
     * 获取分页订单列表数据
     * @return 返回分页数据
     */
    public Page<OrderInfo> getPageList();
}
