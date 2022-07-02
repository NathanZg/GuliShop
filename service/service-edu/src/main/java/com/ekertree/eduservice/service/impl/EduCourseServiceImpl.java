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

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //用于封装的对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        //查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询课程描述
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);

        //将课程信息和描述进行封装
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        BeanUtils.copyProperties(description, courseInfoVo);

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int r = baseMapper.updateById(eduCourse);
        if (r == 0) {
            throw new GuliException(20001, "修改课程信息失败！");
        }
        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(description);
        if (!b) {
            throw new GuliException(20001, "修改描述失败！");
        }
    }
}
