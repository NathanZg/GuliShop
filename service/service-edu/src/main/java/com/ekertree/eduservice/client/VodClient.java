package com.ekertree.eduservice.client;

import com.ekertree.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ClassName: VodClient
 * Description:
 * date: 2022/7/14 19:24
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAliYunVideo/{id}")
    Result removeAliYunVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deleteBatch")
    Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
