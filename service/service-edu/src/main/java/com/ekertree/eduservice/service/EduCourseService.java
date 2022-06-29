package com.ekertree.eduservice.service;

import com.ekertree.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
