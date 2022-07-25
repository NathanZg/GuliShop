package com.ekertree.eduorder.service;

import com.ekertree.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);

    boolean isBuyCourse(String courseId, String memberId);
}
