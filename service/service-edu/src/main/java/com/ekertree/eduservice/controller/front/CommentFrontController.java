package com.ekertree.eduservice.controller.front;

import com.ekertree.commonutils.JwtUtils;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.client.UcenterClient;
import com.ekertree.eduservice.entity.EduComment;
import com.ekertree.eduservice.service.EduCommentService;
import com.ekertree.servicebase.entity.vo.CommentUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CommentFrontController
 * Description:
 * date: 2022/7/23 16:56
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduservice/frontcomment/")
@Api(tags = "评论管理")
public class CommentFrontController {
    private EduCommentService commentService;
    private UcenterClient ucenterClient;

    public CommentFrontController(EduCommentService commentService, UcenterClient ucenterClient) {
        this.commentService = commentService;
        this.ucenterClient = ucenterClient;
    }

    @ApiOperation(value = "评论分页列表")
    @GetMapping("{courseId}/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") long page, @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") long limit,
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable("courseId") String courseId,
            HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        CommentUserVo currentUser = ucenterClient.getInfoUc(memberId);
        Map<String, Object> map = commentService.pageComment(page, limit, courseId);
        if (currentUser != null) {
            map.put("userAvatar",currentUser.getAvatar());
        }
        return Result.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("addComment")
    public Result addComment(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return Result.error().setCode(28004).setMessage("请先登录!");
        }
        comment.setMemberId(memberId);
        CommentUserVo commentUserVo = ucenterClient.getInfoUc(memberId);
        if (commentUserVo != null) {
            comment.setNickname(commentUserVo.getNickname());
            comment.setAvatar(commentUserVo.getAvatar());
        }
        commentService.save(comment);
        return Result.ok();
    }

    @PostMapping("getCommentCounts")
    @ApiOperation("查询课程列表的评论数")
    public Result getCommentCounts(@RequestBody String courseIds){
        if (!StringUtils.isEmpty(courseIds)) {
            List<Integer> countList = commentService.getCommentCounts(courseIds);
            return Result.ok().data("countList",countList);
        }else {
            return Result.ok();
        }
    }
}
