package com.chenzhiwu.beggar.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author:IGG
 * @date:2019/12/12-11 : 34
 */
@Entity(name = "orderInfo")
@Table(name = "order_info")
public class OrderInfo {
    @Id //主键策略，递增
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "delivery_addr_id")
    private Long deliveryAddrId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_count")
    private Integer goodsCount;

    @Column(name = "goods_price")
    private Double goodsPrice;

    @Column(name = "order_channel")
    private Integer orderChannel;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "pay_date")
    private Date payDate;

    public Integer getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }
    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public Integer getGoodsCount() {
        return goodsCount;
    }
    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
    public Double getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public Integer getOrderChannel() {
        return orderChannel;
    }
    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}

