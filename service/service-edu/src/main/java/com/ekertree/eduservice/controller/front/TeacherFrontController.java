package com.ekertree.eduservice.controller.front;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.EduTeacher;
import com.ekertree.eduservice.service.EduCourseService;
import com.ekertree.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: TeacherFrontController
 * Description:
 * date: 2022/7/20 14:56
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduservice/teacherfront/")
@Api(tags = "前端讲师管理")
public class TeacherFrontController {
    private EduTeacherService teacherService;
    private EduCourseService courseService;

    public TeacherFrontController(EduTeacherService teacherService, EduCourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("getFrontPageTeacherList/{current}/{limit}")
    @ApiOperation("前端讲师分页展示")
    public Result getFrontPageTeacherList(@PathVariable("current") long current,
                                            @PathVariable("limit") long limit){
        Page<EduTeacher> page = new Page<EduTeacher>(current, limit);
        Map<String,Object> map = teacherService.getFrontPageTeacherList(page);
        return Result.ok().data(map);
    }

    @GetMapping("getFrontTeacherInfo/{teacherId}")
    @ApiOperation("讲师详情")
    public Result getFrontTeacherInfo(@PathVariable("teacherId") String teacherId){
        //根据讲师id查询讲师信息
        EduTeacher teacher = teacherService.getById(teacherId);
        return Result.ok().data("teacher",teacher);
    }

    @GetMapping("getFrontTeacherCourseList/{teacherId}/{current}/{limit}")
    @ApiOperation("讲师主讲课程")
    public Result getTeacherCourseList(@PathVariable("teacherId") String teacherId,
                                       @PathVariable("current") long current,
                                       @PathVariable("limit") long limit){
        Page<EduCourse> page = courseService.teacherDetailCourseList(teacherId, current, limit);
        return Result.ok().data("total", page.getTotal()).data("teacherCourseList", page.getRecords());
    }
}

