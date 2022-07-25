package com.ekertree.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.vo.CommentQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-23
 */
public interface EduCommentService extends IService<EduComment> {
    Map<String, Object> pageComment(long page, long limit, String courseId);

    Page<EduComment> pageCourse(long current, long limit, String courseId,CommentQuery commentQuery);

    List<Integer> getCommentCounts(String courseIds);
}
