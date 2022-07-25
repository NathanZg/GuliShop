package com.ekertree.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.commonutils.OrderNoUtil;
import com.ekertree.eduorder.client.EduClient;
import com.ekertree.eduorder.client.UcenterClient;
import com.ekertree.eduorder.entity.Order;
import com.ekertree.eduorder.mapper.OrderMapper;
import com.ekertree.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.servicebase.entity.vo.CommentUserVo;
import com.ekertree.servicebase.entity.vo.OrderCourseVo;
import com.ekertree.servicebase.entity.vo.OrderUserVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private EduClient eduClient;
    private UcenterClient ucenterClient;

    public OrderServiceImpl(EduClient eduClient, UcenterClient ucenterClient) {
        this.eduClient = eduClient;
        this.ucenterClient = ucenterClient;
    }

    @Override
    public String saveOrder(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        OrderUserVo orderUserInfo = ucenterClient.getOrderUserInfo(memberId);
        //通过远程调用根据课程id获取课程信息
        OrderCourseVo orderCourseInfo = eduClient.getOrderCourseInfo(courseId);
        //创建order对象
        Order order = new Order();
        //订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        //课程id
        order.setCourseId(courseId);
        //课程名
        order.setCourseTitle(orderCourseInfo.getTitle());
        //课程封面
        order.setCourseCover(orderCourseInfo.getCover());
        //讲师名称
        order.setTeacherName(orderCourseInfo.getTeacherName());
        //价格
        order.setTotalFee(orderCourseInfo.getPrice());
        //用户id
        order.setMemberId(memberId);
        //用户手机
        order.setMobile(orderUserInfo.getMobile());
        //用户昵称
        order.setNickname(orderUserInfo.getNickname());
        //支付状态 0未支付 1 已支付
        order.setStatus(0);
        //支付类型 1 微信支付
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status",1);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            return true;
        }else{
            return false;
        }
    }
}
