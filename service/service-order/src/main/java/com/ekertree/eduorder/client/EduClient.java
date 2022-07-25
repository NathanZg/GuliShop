package com.ekertree.eduorder.client;

import com.ekertree.servicebase.entity.vo.OrderCourseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: EduClient
 * Description:
 * date: 2022/7/24 20:57
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/eduservice/coursefront/getOrderCourseInfo/{courseId}")
    OrderCourseVo getOrderCourseInfo(@PathVariable String courseId);
}
