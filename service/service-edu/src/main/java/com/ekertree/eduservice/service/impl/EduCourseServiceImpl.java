package com.ekertree.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.EduCourseDescription;
import com.ekertree.eduservice.entity.EduTeacher;
import com.ekertree.eduservice.entity.frontvo.CourseFrontVo;
import com.ekertree.eduservice.entity.frontvo.CourseWebVo;
import com.ekertree.eduservice.entity.vo.CourseInfoVo;
import com.ekertree.eduservice.entity.vo.CoursePublishVo;
import com.ekertree.eduservice.entity.vo.CourseQuery;
import com.ekertree.eduservice.mapper.EduCourseMapper;
import com.ekertree.eduservice.service.EduChapterService;
import com.ekertree.eduservice.service.EduCourseDescriptionService;
import com.ekertree.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.eduservice.service.EduVideoService;
import com.ekertree.servicebase.excetionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private EduVideoService eduVideoService;

    private EduChapterService eduChapterService;

    public EduCourseServiceImpl(EduCourseDescriptionService eduCourseDescriptionService, EduVideoService eduVideoService, EduChapterService eduChapterService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
        this.eduVideoService = eduVideoService;
        this.eduChapterService = eduChapterService;
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

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public Page<EduCourse> pageCourse(long current, long limit, CourseQuery courseQuery) {
        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String minPrice = courseQuery.getMinPrice();
        String maxPrice = courseQuery.getMaxPrice();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(minPrice)){
            queryWrapper.gt("price", minPrice);
        }
        if (!StringUtils.isEmpty(maxPrice)){
            queryWrapper.lt("price", maxPrice);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status", status);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.gt("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.lt("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(eduCoursePage, queryWrapper);
        return eduCoursePage;
    }

    @Override
    public void removeCourse(String courseId) {
        //删除课程的小节
        eduVideoService.removeVideoByCourseId(courseId);
        //删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //删除描述
        eduCourseDescriptionService.removeById(courseId);
        //删除课程
        int cnt = baseMapper.deleteById(courseId);
        if (cnt == 0) {
            throw new GuliException(20001, "删除课程失败！");
        }
    }

    @Override
    public boolean isExit(String courseId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("id", courseId);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Cacheable(key = "'selectHotCourse'",value = "hotCourse")
    public List<EduCourse> selectHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.eq("status", "Normal");
        wrapper.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public Page<EduCourse> teacherDetailCourseList(String teacherId,long current,long limit) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        Page<EduCourse> page = new Page<>(current,limit);
        baseMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        //二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        //关注度
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        //最新
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        wrapper.eq("status", "Normal");
        baseMapper.selectPage(pageCourse, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();
        List<EduCourse> records = pageCourse.getRecords();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}
