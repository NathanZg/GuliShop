package com.ekertree.eduservice.service.impl;

import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.EduCourseDescription;
import com.ekertree.eduservice.entity.vo.CourseInfoVo;
import com.ekertree.eduservice.mapper.EduCourseMapper;
import com.ekertree.eduservice.service.EduCourseDescriptionService;
import com.ekertree.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.servicebase.excetionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private EduCourseDescriptionService eduCourseDescriptionService;

    public EduCourseServiceImpl(EduCourseDescriptionService eduCourseDescriptionService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
    }

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //往课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败！");
        }
        //获取添加后的课程id
        String eduCourseId = eduCourse.getId();
        //往课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //使得课程id与课程id描述一致
        eduCourseDescription.setId(eduCourseId);
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourseId;
    }
}
