package com.ekertree.statistics.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.statistics.entity.vo.DailyQuery;
import com.ekertree.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.ErrorState;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-26
 */
@RestController
@RequestMapping("/statistics/")
@Api(tags = "统计")
public class DailyController {

    private DailyService dailyService;

    public DailyController(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @GetMapping("nowChart")
    @ApiOperation("统计今日当时")
    public Result nowChart() {
        Map<String, Object> map = dailyService.nowChart();
        return Result.ok().data(map);
    }

    @PostMapping("showChart")
    @ApiOperation("条件查询统计")
    public Result showChart(@RequestBody(required = false)DailyQuery dailyQuery) {
        Map<String, Object> map = dailyService.getChartData(dailyQuery);
        return Result.ok().data(map);
    }

    @GetMapping("addLoginNum")
    @ApiOperation("访问量加一")
    public Result addLoginNum() {
        dailyService.addLoginNum();
        return Result.ok();
    }

    @GetMapping("addVideoView")
    @ApiOperation("视频播放量加一")
    public Result addVideoView() {
        dailyService.addVideoView();
        return Result.ok();
    }
}

