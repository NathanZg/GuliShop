package com.ekertree.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * ClassName: OrderClientImpl
 * Description:
 * date: 2022/7/25 19:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class OrderClientImpl implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
