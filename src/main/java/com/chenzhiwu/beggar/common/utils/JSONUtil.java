package com.chenzhiwu.beggar.common.utils;

/**
 * @author igg zhaoyg
 * @date 2019/11/7 17:56
 */
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: JSONUtil
 * @description:
 * @author: IGG zhaoyg
 * @Date: 2019年8月9日 下午5:34:47
 */

public class JSONUtil {

    public static JSONObject toJSONObject(Object object) {
        String jsonStr = JSON.toJSONString(object);
        return JSONObject.parseObject(jsonStr);
    }

    public static JSONObject toJSONObject(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }

    public static <T> T toObject(String jsonStr,Class<T> elementType) {
        return JSONObject.parseObject(jsonStr, elementType);
    }

    public static JSONArray toJSONArray(Object object) {
        String jsonStr = JSON.toJSONString(object);
        return JSONArray.parseArray(jsonStr);
    }

    /**
     * 解决C端list为空 不返回[] 而是 "" 问题
     * @param jsonObject
     * @return
     */
    public static JSONArray getJSONArrayWithList(JSONObject jsonObject) {
        if(StringUtils.isBlank(jsonObject.getString("List"))) {
            return new JSONArray();
        }
        return jsonObject.getJSONArray("List");
    }

    public static boolean isJSON(String jsonStr) {
        if(StringUtils.isBlank(jsonStr)) {
            return false;
        }
        try {
            toJSONObject(jsonStr);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
