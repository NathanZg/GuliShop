package com.ekertree.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.eduservice.entity.vo.TeacherQuery;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-19
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Page<EduTeacher> pageTeacher(long current, long limit, TeacherQuery teacherQuery);

    List<EduTeacher> selectHotTeacher();
}
