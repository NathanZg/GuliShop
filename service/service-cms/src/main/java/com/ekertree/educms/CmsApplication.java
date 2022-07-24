package com.ekertree.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: CmsApplication
 * Description:
 * date: 2022/7/15 23:06
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ekertree")
@MapperScan("com.ekertree.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
