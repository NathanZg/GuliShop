package com.ekertree.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: VodApplication
 * Description:
 * date: 2022/7/13 18:14
 *
 * @author Ekertree
 * @since JDK 1.8
 */
//只是做视频的相关操作，所以忽略数据库的配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.ekertree")//为了使用swagger和Result类
@EnableDiscoveryClient
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
