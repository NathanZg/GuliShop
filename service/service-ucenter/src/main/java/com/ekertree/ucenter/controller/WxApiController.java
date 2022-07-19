package com.ekertree.ucenter.controller;

import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ekertree.ucenter.entity.Member;
import com.ekertree.ucenter.service.MemberService;
import com.ekertree.ucenter.utils.ConstantWxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * ClassName: WxApiController
 * Description:
 * date: 2022/7/19 16:32
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Controller
@CrossOrigin
@Api(tags = "微信登陆")
@RequestMapping("/api/ucenter/wx/")
public class WxApiController {

    private MemberService memberService;

    public WxApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("login")
    @ApiOperation("获取微信二维码地址")
    public String getWxCode() {
        //重定向
        return "redirect:" + memberService.redirectUrl();
    }

    @GetMapping("callback")
    @ApiOperation("获取微信用户信息")
    public String callback(String code,String state) {
        String token =  memberService.callback(code,state);
        return "redirect:http://localhost:3000?token=" + token;
    }
}
