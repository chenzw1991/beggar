package com.chenzhiwu.beggar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//jpa dao 扫描路径
@EnableJpaRepositories(basePackages = "com.chenzhiwu.beggar.dao")
//实体扫描路径
@EntityScan(basePackages = "com.chenzhiwu.beggar.pojo")
////开启缓存注解
@EnableCaching
@EnableJpaAuditing // 使用jpa自动赋值
public class BeggarApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeggarApplication.class, args);
    }

}
