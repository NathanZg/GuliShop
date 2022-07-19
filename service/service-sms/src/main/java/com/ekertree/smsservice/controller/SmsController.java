package com.ekertree.smsservice.controller;

import com.ekertree.commonutils.Result;
import com.ekertree.smsservice.service.SmsService;
import com.ekertree.smsservice.utils.KeyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.params.HttpParams;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SmsController
 * Description:
 * date: 2022/7/17 21:46
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/edusms/sms")
@CrossOrigin
@Api(tags = "短信发送")
public class SmsController {

    private SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("sendCommonCode/{phone}")
    @ApiOperation("发送通用验证码")
    public Result sendCommonCode(@PathVariable String phone) {
        boolean flag = smsService.sendCommonCode(phone);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error().setMessage("短信发送失败！");
        }
    }

    @GetMapping("sendLoginCode/{phone}")
    @ApiOperation("发送登陆验证码")
    public Result sendLoginCode(@PathVariable String phone) {
        boolean flag = smsService.sendLoginCode(phone);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error().setMessage("短信发送失败！");
        }
    }

    @GetMapping("sendRegisterCode/{phone}")
    @ApiOperation("发送注册验证码")
    public Result sendRegisterCode(@PathVariable String phone) {
        boolean flag = smsService.sendRegisterCode(phone);
        if (flag) {
            return Result.ok();
        }else {
            return Result.error().setMessage("短信发送失败！");
        }
    }
}
