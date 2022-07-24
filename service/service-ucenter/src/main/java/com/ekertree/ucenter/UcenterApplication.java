package com.ekertree.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: UcenterApplication
 * Description:
 * date: 2022/7/18 14:45
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan("com.ekertree")
@MapperScan("com.ekertree.ucenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
