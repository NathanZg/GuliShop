package com.ekertree.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.ekertree.commonutils.JwtUtils;
import com.ekertree.commonutils.Result;
import com.ekertree.eduorder.entity.Order;
import com.ekertree.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/eduorder/order/")
@Api(tags = "订单管理")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("createOrder/{courseId}")
    public Result save(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.saveOrder(courseId,
                JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("orderNo", orderNo);
    }

    @GetMapping("getOrderInfo/{orderNo}")
    @ApiOperation("根据订单号查询订单信息")
    public Result getOrderInfo(@PathVariable("orderNo") String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("order", order);
    }

    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    @ApiOperation("根据课程id和用户id判断课程是否被购买")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId) {
        return orderService.isBuyCourse(courseId,memberId);
    }
}

