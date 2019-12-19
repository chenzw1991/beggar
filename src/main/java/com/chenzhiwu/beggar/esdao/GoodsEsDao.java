package com.chenzhiwu.beggar.esdao;/*
@author:Administrator
@date:2019/12/18 22 30
*/

import com.chenzhiwu.beggar.pojo.GoodsEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface GoodsEsDao extends ElasticsearchRepository<GoodsEs,Long> {
}
