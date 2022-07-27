package com.ekertree.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduComment;
import com.ekertree.eduservice.entity.EduCourse;
import com.ekertree.eduservice.entity.vo.CommentQuery;
import com.ekertree.eduservice.entity.vo.CourseQuery;
import com.ekertree.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-23
 */
@RestController
@RequestMapping("/eduservice/comment")
@Api(tags = "评论后台管理")
public class EduCommentController {

    private EduCommentService commentService;

    public EduCommentController(EduCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("pageComment/{courseId}/{current}/{limit}")
    @ApiOperation("分页条件查询课程评论")
    public Result pageCommentCondition(
            @PathVariable("courseId") String courseId,
            @PathVariable("current") long current,
            @PathVariable("limit") long limit,
            @RequestBody(required = false) CommentQuery commentQuery) {
        Page<EduComment> commentPage = commentService.pageCourse(current, limit, courseId, commentQuery);
        return Result.ok().data("total", commentPage.getTotal())
                .data("items", commentPage.getRecords());
    }

    @DeleteMapping("deleteComment/{commentId}")
    @ApiOperation("根据id删除评论")
    public Result deleteCommentById(@PathVariable("commentId") String commentId) {
        boolean flag = commentService.removeById(commentId);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error().setMessage("删除评论失败！");
        }
    }
}

