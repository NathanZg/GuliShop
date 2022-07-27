package com.ekertree.statistics.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName: EduClient
 * Description:
 * date: 2022/7/27 13:57
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient(name = "service-edu")
public interface EduClient {
    @GetMapping("/eduservice/course/getCourseCount")
    Integer getCourseCount();
}
