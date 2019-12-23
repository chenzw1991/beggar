package com.chenzhiwu.beggar.dao;

import com.chenzhiwu.beggar.pojo.MiaoshaGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:IGG
 * @date:2019/12/23-13 : 45
 */
public interface MiaoshaGoodsDao  extends JpaRepository<MiaoshaGoods, Long>, JpaSpecificationExecutor<MiaoshaGoods> {
    //通过商品id查询秒杀信息
    @Query(nativeQuery = true, value = "SELECT * from miaosha_goods where goods_id = ?1")
    MiaoshaGoods getMsInfoByGoodId(Long goodId);

    //通过商品id删除秒杀信息
    @Modifying
    @Query(nativeQuery = true, value = "delete from miaosha_goods where goods_id = ?1")
    public void delMsInfoByGoodId(Long goodId);
}
