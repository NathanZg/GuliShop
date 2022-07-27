package com.ekertree.eduservice.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.subject.OneSubject;
import com.ekertree.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.One;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/eduservice/subject/")
@Api(tags = "课程分类管理")
public class EduSubjectController {

    private EduSubjectService eduSubjectService;

    public EduSubjectController(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @PostMapping("addSubject")
    @ApiOperation("上传excel表格添加课程")
    public Result addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return Result.ok();
    }

    @GetMapping("getAllSubject")
    @ApiOperation("树型课程分类列表")
    public Result getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return Result.ok().data("list", list);
    }

}

