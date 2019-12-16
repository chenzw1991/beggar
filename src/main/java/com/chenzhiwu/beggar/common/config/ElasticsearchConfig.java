package com.chenzhiwu.beggar.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.chenzhiwu.beggar.common.utils.JsonUtils;

import org.aspectj.weaver.ast.Test;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.io.IOException;
import java.util.Map;

/**
 * Description :
 * Author : Chen.MeiJie
 * Create Time : 2019/1/28.
 */
@Configuration
public class ElasticsearchConfig {
    private static Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);
    @Bean("elasticsearchTemplate")
    public ElasticsearchTemplate getElasticsearch(Client client, ElasticsearchConverter converter) {
        return new ElasticsearchTemplate(client, converter, new JacksonEntiryMapper());
    }

    private static class JacksonEntiryMapper implements EntityMapper {
        private ObjectMapper mapper;

        public JacksonEntiryMapper() {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
            mapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);
        }

        @Override
        public String mapToString(Object o) throws IOException {
            String value = mapper.writeValueAsString(o);
            return value;
        }

        @Override
        public <T> T mapToObject(String s, Class<T> clazz) throws IOException {
            T t = mapper.readValue(s, clazz);
            return t;
        }
    }
}
