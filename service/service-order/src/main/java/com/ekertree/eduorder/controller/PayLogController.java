package com.ekertree.eduorder.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.ErrorState;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/eduorder/paylog/")
@CrossOrigin
@Api("微信二维码")
public class PayLogController {
    private PayLogService payLogService;

    public PayLogController(PayLogService payLogService) {
        this.payLogService = payLogService;
    }

    @GetMapping("createNative/{orderNo}")
    @ApiOperation("生成微信支付二维码")
    public Result createNative(@PathVariable("orderNo") String orderNo) {
        //根据orderNo返回二维码地址，和其他需要的信息
        Map<String,Object> map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    @GetMapping("queryOrderStatus/{orderNo}")
    @ApiOperation("根据订单号查询订单支付状态")
    public Result queryOrderStatus(@PathVariable("orderNo") String orderNo) {
        Map<String,String> map = payLogService.queryOrderStatus(orderNo);
        if (map == null) {
            return Result.error().setMessage("支付出错了！");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表状态
            payLogService.updateOrderStatus(map);
            return Result.ok().setMessage("支付成功！");
        }
        return Result.ok().setCode(25000).setMessage("正在支付中....");
    }
}

