package com.chenzhiwu.beggar.component.thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chenzhiwu.beggar.component.thymeleaf.TimoDialect;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Configuration
public class ThymeleafConfig {

    /**
     * 配置自定义的CusDialect，用于整合thymeleaf模板
     */
    @Bean
    public TimoDialect getTimoDialect(){
        return new TimoDialect();
    }
}
