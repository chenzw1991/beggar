package com.chenzhiwu.beggar.common.redis;

/**
 * @author:IGG
 * @date:2019/12/24-10 : 26
 */
public class GoodsKey  extends BasePrefix{
    public GoodsKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }
    //goods_detail页面       1分钟
    public static GoodsKey getGoodsDetail=new GoodsKey(60,"gd");
    //msgoods_detail页面       1分钟
    public static GoodsKey getMsGoodsDetail=new GoodsKey(60,"msgd");
    //msgoods_list页面          1分钟
    public static GoodsKey getMsGoodsList=new GoodsKey(60,"msgl");
}
