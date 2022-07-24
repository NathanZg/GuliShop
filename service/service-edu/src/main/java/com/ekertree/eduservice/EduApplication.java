package com.ekertree.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: EduApplication
 * Description:
 * date: 2022/6/20 8:31
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@SpringBootApplication
@MapperScan("com.ekertree.eduservice.mapper")
@ComponentScan(basePackages = {"com.ekertree"})
@EnableDiscoveryClient//nacos注册
@EnableFeignClients//服务调用
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
