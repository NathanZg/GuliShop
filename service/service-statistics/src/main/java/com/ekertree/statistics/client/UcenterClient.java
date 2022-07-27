package com.ekertree.statistics.client;

import com.ekertree.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: UcenterClient
 * Description:
 * date: 2022/7/26 15:05
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/countRegister/{day}")
    @ApiOperation("统计每天的注册人数")
    Integer countRegister(@PathVariable("day") String day);
}
