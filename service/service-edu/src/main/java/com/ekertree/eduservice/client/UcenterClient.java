package com.ekertree.eduservice.client;

import com.ekertree.commonutils.Result;
import com.ekertree.servicebase.entity.vo.CommentUserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: UcenterClient
 * Description:
 * date: 2022/7/23 16:52
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/getInfoUc/{id}")
    @ApiOperation("实现用户id获取用户信息")
    CommentUserVo getInfoUc(@PathVariable("id") String id);
}
