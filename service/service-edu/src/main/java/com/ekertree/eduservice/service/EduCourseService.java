package com.ekertree.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.eduservice.entity.vo.CourseInfoVo;
import com.ekertree.eduservice.entity.vo.CoursePublishVo;
import com.ekertree.eduservice.entity.vo.CourseQuery;

import java.util.List;

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

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    Page<EduCourse> pageCourse(long current, long limit, CourseQuery courseQuery);

    void removeCourse(String courseId);

    boolean isExit(String courseId);

    List<EduCourse> selectHotCourse();
}
