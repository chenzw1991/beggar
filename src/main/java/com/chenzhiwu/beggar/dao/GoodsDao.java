package com.chenzhiwu.beggar.dao;

import com.chenzhiwu.beggar.pojo.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author:IGG
 * @date:2019/12/12-17 : 03
 */
public interface GoodsDao extends JpaRepository<Goods, Long> , JpaSpecificationExecutor<Goods> {
    public Goods getGoodsById(Long id);

    @Query("from goods where current_date > upshelf_time and current_date < downshelf_time order by id")
    public List<Goods> getGoodsPage(Pageable pageable);

    //库存减1
    @Modifying
    @Query(nativeQuery = true, value = "update goods m set m.goods_stock=m.goods_stock - 1 where m.id=?1 and m.goods_stock>0")
    public void reduceStock(Long id);

    //批量查询
    @Query(value = "select * from goods WHERE  in(?1) ORDER BY id asc",nativeQuery = true)
    List<Goods> batchGetGoodsByIdList(List<Long> idList);

    //更新上下架时间
    @Modifying
    @Query(nativeQuery = true, value = "update goods m set m.upshelf_time=?2,m.downshelf_time=?3 where m.id=?1")
    public void updateShelfTime(Long id, String upshelfTime, String downshelfTime);
}