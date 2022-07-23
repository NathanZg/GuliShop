package com.ekertree.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.chapter.ChapterVo;
import com.ekertree.eduservice.entity.frontvo.CourseFrontVo;
import com.ekertree.eduservice.entity.frontvo.CourseWebVo;
import com.ekertree.eduservice.service.EduChapterService;
import com.ekertree.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CourseFrontController
 * Description:
 * date: 2022/7/20 18:44
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduservice/coursefront/")
@CrossOrigin
@Api(tags = "课程前端")
public class CourseFrontController {

    private EduCourseService courseService;

    private EduChapterService chapterService;

    public CourseFrontController(EduCourseService courseService, EduChapterService chapterService) {
        this.courseService = courseService;
        this.chapterService = chapterService;
    }

    @PostMapping(value = "getCourseFrontList/{current}/{limit}")
    @ApiOperation("前端课程分页条件查询")
    public Result getCourseFrontList(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<EduCourse>(current, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        return Result.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    @ApiOperation("课程详情信息")
    public Result getFrontCourseInfo(@PathVariable("courseId") String courseId) {
        //查课程
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //查小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("courseWebVo", courseWebVo).data("chapterVideoList",chapterVideoList);
    }

}
