package com.ekertree.statistics.service;

import com.ekertree.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.statistics.entity.vo.DailyQuery;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-26
 */
public interface DailyService extends IService<Daily> {

    void createStatistics(String day);

    Map<String, Object> getChartData(DailyQuery dailyQuery);

    Map<String, Object> nowChart();

    void addLoginNum();

    void addVideoView();
}
