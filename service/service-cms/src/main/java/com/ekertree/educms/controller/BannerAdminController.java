package com.ekertree.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.educms.entity.CrmBanner;
import com.ekertree.educms.entity.vo.BannerVo;
import com.ekertree.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/educms/bannerAdmin/")
@Api(tags = "Banner图后台管理")
public class BannerAdminController {

    private CrmBannerService bannerService;

    public BannerAdminController(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping("pageBanner/{current}/{limit}")
    @ApiOperation("分页条件查询Banner")
    public Result pageBanner(@PathVariable("current") long current,
                             @PathVariable("limit") long limit,
                             @RequestBody(required = false)BannerVo bannerVo) {
        Page<CrmBanner> bannerPage = bannerService.pageBanner(current, limit, bannerVo);
        return Result.ok().data("total", bannerPage.getTotal())
                .data("items", bannerPage.getRecords());
    }

    @PutMapping("addBanner")
    @ApiOperation("添加Bannner")
    @CacheEvict(key = "'selectBannerList'",value = "bannerList")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return Result.ok();
    }

    @GetMapping("getBanner/{id}")
    @ApiOperation("根据id获取Banner")
    public Result getBannerById(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("banner", banner);
    }

    @PostMapping("updateBanner")
    @ApiOperation("修改Bannner")
    @CacheEvict(key = "'selectBannerList'",value = "bannerList")
    public Result updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return Result.ok();
    }

    @DeleteMapping("removeBanner/{id}")
    @ApiOperation("删除Bannner")
    @CacheEvict(key = "'selectBannerList'",value = "bannerList")
    public Result removeBannerById(@PathVariable("id") String id) {
        bannerService.removeById(id);
        return Result.ok();
    }
}

