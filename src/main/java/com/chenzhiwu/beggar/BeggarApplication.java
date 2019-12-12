package com.chenzhiwu.beggar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//jpa dao 扫描路径
@EnableJpaRepositories(basePackages = "com.chenzhiwu.beggar.dao")
//实体扫描路径
@EntityScan(basePackages = "com.chenzhiwu.beggar.pojo")
public class BeggarApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeggarApplication.class, args);
    }

}
