package com.ekertree.eduservice.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.chapter.ChapterVo;
import com.ekertree.eduservice.service.EduChapterService;
import com.ekertree.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/eduservice/chapter/")
@CrossOrigin
@Api(tags = "课程章节管理")
public class EduChapterController {

    private EduChapterService eduChapterService;

    public EduChapterController(EduChapterService eduChapterService) {
        this.eduChapterService = eduChapterService;
    }

    @GetMapping("getChapterVideo/{courseId}")
    @ApiOperation("获得所有课程章节和小节")
    public Result getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("ChapterVideos", list);
    }
}

