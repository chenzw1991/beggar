package com.chenzhiwu.beggar.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description :
 * Author : Chen.MeiJie
 * Create Time : 2018/11/30.
 */
public class JsonUtils {
    private static ObjectMapper mapper;
    private static ObjectMapper mapperIgnoreCase;
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapperIgnoreCase = new ObjectMapper();
        mapperIgnoreCase.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperIgnoreCase.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        logger.info("JsonUtils initialize, objectMapper set 'FAIL_ON_UNKNOWN_PROPERTIES' to false");
    }

    public static String writeValueAsString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            logger.warn("object cannot be serialized", e);
            return null;
        }
    }

    public static <T> T readValue(ObjectMapper mapper, String jsonstr, Class<T> clazz) {
        try {
            return mapper.readValue(jsonstr, clazz);
        } catch (Exception e) {
            logger.warn("content cannot be deserialized : " + jsonstr, e);
            return null;
        }
    }

    public static <T> T readValue(String jsonstr, Class<T> clazz) {
        return readValue(mapper, jsonstr, clazz);
    }

    public static <T> T readValueIgnoreCase(String jsonstr, Class<T> clazz) {
        return readValue(mapperIgnoreCase, jsonstr, clazz);
    }

    /**
     * 反序列化
     *
     * @param mapper
     * @param jsonstr
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T readValue(ObjectMapper mapper, String jsonstr, TypeReference reference) {
        try {
            return mapper.readValue(jsonstr, reference);
        } catch (Exception e) {
            logger.warn("content cannot be deserialized : " + jsonstr, e);
            return null;
        }
    }

    public static <T> T readValue(String jsonstr, TypeReference reference) {
        return readValue(mapper, jsonstr, reference);
    }

    public static <T> T readValueIgnoreCase(String jsonstr, TypeReference reference) {
        return readValue(mapperIgnoreCase, jsonstr, reference);
    }
}
