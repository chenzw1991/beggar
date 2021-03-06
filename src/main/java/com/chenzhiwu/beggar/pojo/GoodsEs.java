package com.chenzhiwu.beggar.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
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
@Document(indexName = "beggarshop",type = "goods", shards = 1, replicas = 0)
@Data
public class GoodsEs {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String goods_name;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String goods_title;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date upshelf_time;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date downshelf_time;

    @Field(type = FieldType.Integer)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public Date getUpshelf_time() {
        return upshelf_time;
    }

    public void setUpshelf_time(Date upshelf_time) {
        this.upshelf_time = upshelf_time;
    }

    public Date getDownshelf_time() {
        return downshelf_time;
    }

    public void setDownshelf_time(Date downshelf_time) {
        this.downshelf_time = downshelf_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
