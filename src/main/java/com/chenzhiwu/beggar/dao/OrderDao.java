package com.chenzhiwu.beggar.dao;

import com.chenzhiwu.beggar.pojo.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author:IGG
 * @date:2019/12/12-17 : 10
 */
public interface OrderDao extends JpaRepository<OrderInfo, Long> , JpaSpecificationExecutor<OrderInfo> {
}
