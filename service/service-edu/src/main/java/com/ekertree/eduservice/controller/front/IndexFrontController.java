package com.ekertree.eduservice.controller.front;

import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.EduTeacher;
import com.ekertree.eduservice.service.EduCourseService;
import com.ekertree.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: IndexFrontController
 * Description:
 * date: 2022/7/16 15:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RequestMapping("/eduservice/indexfront/")
@RestController
@Api(tags = "前端查询")
public class IndexFrontController {

    private EduCourseService courseService;
    private EduTeacherService teacherService;

    public IndexFrontController(EduCourseService courseService, EduTeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("index")
    public Result index() {
        //查询前八条热门课程
        List<EduCourse> courseList = courseService.selectHotCourse();

        //查询任职最久的四名讲师
        List<EduTeacher> teacherList = teacherService.selectHotTeacher();

        return Result.ok().data("courseList", courseList)
                .data("teacherList", teacherList);
    }
}
