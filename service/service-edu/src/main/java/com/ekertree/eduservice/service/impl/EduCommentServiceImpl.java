package com.ekertree.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.eduservice.entity.EduComment;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.vo.CommentQuery;
import com.ekertree.eduservice.mapper.EduCommentMapper;
import com.ekertree.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-23
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> pageComment(long page, long limit, String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }

    @Override
    public Page<EduComment> pageCourse(long current, long limit,String courseId, CommentQuery commentQuery) {
        Page<EduComment> page = new Page<>(current,limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        String content = commentQuery.getContent();
        String begin = commentQuery.getBegin();
        String end = commentQuery.getEnd();
        String memberId = commentQuery.getMemberId();
        String nickname = commentQuery.getNickname();
        if (!StringUtils.isEmpty(courseId)){
            queryWrapper.eq("course_id", courseId);
        }
        if (!StringUtils.isEmpty(content)){
            queryWrapper.eq("content", content);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.lt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.lt("gmt_create", end);
        }
        if (!StringUtils.isEmpty(memberId)){
            queryWrapper.like("member_id", memberId);
        }
        if (!StringUtils.isEmpty(nickname)){
            queryWrapper.like("nickname", nickname);
        }
        baseMapper.selectPage(page,queryWrapper);
        return page;
    }
}
