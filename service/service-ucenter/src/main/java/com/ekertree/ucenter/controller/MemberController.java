package com.ekertree.ucenter.controller;


import com.ekertree.commonutils.JwtUtils;
import com.ekertree.commonutils.Result;
import com.ekertree.servicebase.entity.vo.CommentUserVo;
import com.ekertree.servicebase.entity.vo.OrderUserVo;
import com.ekertree.ucenter.entity.Member;
import com.ekertree.ucenter.entity.vo.LoginVo;
import com.ekertree.ucenter.entity.vo.RegisterVo;
import com.ekertree.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-18
 */
@RestController
@RequestMapping("/ucenter/member/")
@CrossOrigin
@Api(tags = "用户管理")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("login")
    @ApiOperation("登陆")
    public Result loginUser(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return Result.ok().data("token", token);
    }

    @PostMapping("register")
    @ApiOperation("注册")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("getMemberInfo")
    @ApiOperation("根据token获取用户信息")
    public Result getMemberInfo(HttpServletRequest request) {
        //根据token获取id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //通过id获取信息
        Member member = memberService.getById(memberId);
        return Result.ok().data("userInfo", member);
    }

    @GetMapping("getInfoUc/{id}")
    @ApiOperation("实现用户id获取评论用户信息")
    public CommentUserVo getInfoUc(@PathVariable("id") String id) {
        Member member = memberService.getById(id);
        CommentUserVo commentUserVo = new CommentUserVo();
        BeanUtils.copyProperties(member, commentUserVo);
        return commentUserVo;
    }

    @GetMapping("getOrderUserInfo/{id}")
    @ApiOperation("实现用户id获取订单用户信息")
    public OrderUserVo getOrderUserInfo(@PathVariable("id") String id) {
        Member member = memberService.getById(id);
        OrderUserVo orderUserVo = new OrderUserVo();
        BeanUtils.copyProperties(member, orderUserVo);
        return orderUserVo;
    }
}

