package com.ekertree.eduorder.service;

import com.ekertree.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryOrderStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
