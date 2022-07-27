package com.ekertree.eduservice.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduChapter;
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

    @PostMapping("addChapter")
    @ApiOperation("添加章节")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return Result.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    @ApiOperation("获取章节信息")
    public Result getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return Result.ok().data("chapter", chapter);
    }

    @PutMapping("updateChapter")
    @ApiOperation("更新章节信息")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }

    @DeleteMapping("{chapterId}")
    @ApiOperation("根据id删除章节")
    public Result deleteChapter(@PathVariable String chapterId){
        boolean res = eduChapterService.deleteChapter(chapterId);
        return res == true ? Result.ok() : Result.error();
    }
}

