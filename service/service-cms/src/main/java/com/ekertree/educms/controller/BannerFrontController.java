package com.ekertree.educms.controller;

import com.ekertree.commonutils.Result;
import com.ekertree.educms.entity.CrmBanner;
import com.ekertree.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.Naming;
import java.util.List;

/**
 * ClassName: BannerFrontController
 * Description:
 * date: 2022/7/16 0:02
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/educms/bannerfront/")
@CrossOrigin
@Api(tags = "Banner图前端管理")
public class BannerFrontController {

    private CrmBannerService bannerService;

    public BannerFrontController(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("getAllBanner")
    @ApiOperation("查询所有Banner")
    public Result getAllBanner() {
        List<CrmBanner> list =  bannerService.selectAllBanners();
        return Result.ok().data("bannerList", list);
    }
}
