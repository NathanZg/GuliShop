package com.ekertree.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@ComponentScan(basePackages = {"com.ekertree"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
