package com.ekertree.statistics.schedule;

import com.ekertree.commonutils.DateUtil;
import com.ekertree.statistics.service.DailyService;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: ScheduledTask
 * Description:定时任务
 * date: 2022/7/26 16:13
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class ScheduledTask {

    private DailyService dailyService;

    public ScheduledTask(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @Scheduled(cron = "0 1 0 * * ? ")
    public void createStatistics(){
        dailyService.createStatistics(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
