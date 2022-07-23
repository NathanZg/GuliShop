package com.ekertree.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduTeacher;
import com.ekertree.eduservice.entity.vo.TeacherQuery;
import com.ekertree.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/eduservice/teacher/")
@Api(tags = "讲师管理")
@CrossOrigin
public class EduTeacherController {

    private EduTeacherService eduTeacherService;

    public EduTeacherController(EduTeacherService eduTeacherService) {
        this.eduTeacherService = eduTeacherService;
    }

    @GetMapping("findAll")
    @ApiOperation("所有讲师列表")
    public Result findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items", list);
    }

    @DeleteMapping("{id}")
    @ApiOperation("逻辑删除对应id的讲师")
    @CacheEvict(key = "'selectHotTeacher'",value = "hotTeacher")
    public Result  removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    /*@GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation("分页查询")
    public Result pageListTeacher(@ApiParam(name = "current", value = "当前页面", required = true) @PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页显示条数", required = true)@PathVariable long limit){
        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        eduTeacherService.page(eduTeacherPage, null);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return Result.ok().data("total", eduTeacherPage.getTotal()).data("items",records);
    }*/

    @PostMapping("pageTeacher/{current}/{limit}")
    @ApiOperation("分页条件查询")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "当前页面", required = true) @PathVariable long current,
                                       @ApiParam(name = "limit", value = "每页显示条数", required = true)@PathVariable long limit,
                                       @ApiParam(name = "teacherQuery", value = "查询条件", required = false) @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> eduTeacherPage = eduTeacherService.pageTeacher(current,limit,teacherQuery);
        return Result.ok().data("total", eduTeacherPage.getTotal()).data("items",eduTeacherPage.getRecords());
    }

    @PutMapping("addTeacher")
    @ApiOperation("添加教师")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("getTeacher/{id}")
    @ApiOperation("根据id查询讲师")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @PostMapping("updateTeacher")
    @ApiOperation("更新讲师信息")
    @CacheEvict(key = "'selectHotTeacher'",value = "hotTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

