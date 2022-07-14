package com.ekertree.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.vo.CourseInfoVo;
import com.ekertree.eduservice.entity.vo.CoursePublishVo;
import com.ekertree.eduservice.entity.vo.CourseQuery;
import com.ekertree.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/eduservice/course/")
@CrossOrigin
@Api(tags = "课程管理")
public class EduCourseController {

    private EduCourseService eduCourseService;

    public EduCourseController(EduCourseService eduCourseService) {
        this.eduCourseService = eduCourseService;
    }

    @PostMapping("addCourseInfo")
    @ApiOperation("添加课程信息")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",courseId);
    }

    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation("根据课程id查询课程基本信息")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo", courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    @ApiOperation("修改课程信息")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    @ApiOperation("获得最终发布信息")
    public Result getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublish = eduCourseService.getPublishCourseInfo(id);
        return Result.ok().data("coursePublish",coursePublish);
    }

    @PostMapping("publishCourse/{courseId}")
    @ApiOperation("修改课程状态")
    public Result publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    @PostMapping("pageCourse/{current}/{limit}")
    @ApiOperation("分页查询课程")
    public Result pageTeacherCondition(@PathVariable long current,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> eduCoursePage = eduCourseService.pageCourse(current, limit, courseQuery);
        return Result.ok().data("total", eduCoursePage.getTotal())
                .data("items", eduCoursePage.getRecords());
    }

    @DeleteMapping("{courseId}")
    @ApiOperation("根据课程id删除课程")
    public Result deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return Result.ok();
    }

    @GetMapping("isExit/{courseId}")
    @ApiOperation("判断该课程是否存在")
    public Result isExit(@PathVariable String courseId) {
        boolean isExit = eduCourseService.isExit(courseId);
        return Result.ok().data("isExit", isExit);
    }
}

