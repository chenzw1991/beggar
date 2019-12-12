package com.chenzhiwu.beggar.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:IGG
 * @date:2019/12/12-11 : 29
 */
@Entity(name = "miaoshaGoods")
@Table(name = "miaosha_goods")
public class MiaoshaGoods {
    @Id //主键策略，递增
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "miaosha_price")
    private Double miaoshaPrice;

    @Column(name = "stock_count")
    private Integer stockCount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }
    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getStockCount() {
        return stockCount;
    }
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
