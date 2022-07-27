package com.ekertree.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName: StatisticsApplication
 * Description:
 * date: 2022/7/26 14:49
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan("com.ekertree")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@MapperScan("com.ekertree.statistics.mapper")
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
