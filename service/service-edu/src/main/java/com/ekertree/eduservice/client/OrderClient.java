package com.ekertree.eduservice.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: OrderClient
 * Description:
 * date: 2022/7/25 19:18
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    @ApiOperation("根据课程id和用户id判断课程是否被购买")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
