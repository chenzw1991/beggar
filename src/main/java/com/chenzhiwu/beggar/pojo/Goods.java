package com.chenzhiwu.beggar.pojo;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author:IGG
 * @date:2019/12/12-11 : 15
 */
@Entity(name = "goods")
@Table(name = "goods")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Goods implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id //主键策略，递增
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_title")
    private String goodsTitle;

    @Column(name = "goods_img")
    private String goodsImg;

    @Column(name = "goods_detail")
    private String goodsDetail;

    @Column(name = "goods_price")
    private Double goodsPrice;

    @Column(name = "goods_stock")
    private Integer goodsStock;

    @Column(name = "upshelf_time")
    private Date upshelfTime;

    @Column(name = "downshelf_time")
    private Date downshelfTime;

    @Column(name = "total_stock")
    private Integer totalStock;

    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Date getUpshelfTime() {
        return upshelfTime;
    }

    public void setUpshelfTime(Date upshelfTime) {
        this.upshelfTime = upshelfTime;
    }

    public Date getDownshelfTime() {
        return downshelfTime;
    }

    public void setDownshelfTime(Date downshelfTime) {
        this.downshelfTime = downshelfTime;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
