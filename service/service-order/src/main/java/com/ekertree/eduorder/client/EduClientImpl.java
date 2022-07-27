package com.ekertree.eduorder.client;

import com.ekertree.commonutils.Result;
import com.ekertree.servicebase.entity.vo.OrderCourseVo;

/**
 * ClassName: EduClientImpl
 * Description:
 * date: 2022/7/24 21:56
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class EduClientImpl implements EduClient{

    @Override
    public OrderCourseVo getOrderCourseInfo(String courseId) {
        return null;
    }

    @Override
    public Result addBuyCount(String courseId) {
        return Result.error();
    }
}
