package com.chenzhiwu.beggar.pojo;

/**
 * @author:IGG
 * @date:2019/12/23-17 : 04
 */
//模糊查询请求包
public class SearchGoodsReqVo {
    private Integer searchPageid;

    private String searchName;

    public Integer getSearchPageid() {
        return searchPageid;
    }

    public void setSearchPageid(Integer searchPageid) {
        this.searchPageid = searchPageid;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
