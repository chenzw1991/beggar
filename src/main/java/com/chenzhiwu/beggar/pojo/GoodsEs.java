package com.chenzhiwu.beggar.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author:IGG
 * @date:2019/12/19-09 : 51
 */
@Document(indexName = "beggar",type = "goods", shards = 1, replicas = 0)
public class GoodsEs {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String goodsName;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String goodsTitle;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date upshelfTime;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date downshelfTime;

    @Field(type = FieldType.Integer)
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
