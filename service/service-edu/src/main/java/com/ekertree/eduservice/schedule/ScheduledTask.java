package com.ekertree.eduservice.schedule;

import com.ekertree.commonutils.DateUtil;
import com.ekertree.eduservice.service.EduCourseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: ScheduledTask
 * Description:
 * date: 2022/7/28 12:21
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class ScheduledTask {
    private EduCourseService courseService;

    public ScheduledTask(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void saveCourseViewCount(){
        courseService.saveCourseViewCount();
    }
}
