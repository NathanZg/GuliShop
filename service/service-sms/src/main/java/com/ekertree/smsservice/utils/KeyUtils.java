package com.ekertree.smsservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: KeyUtils
 * Description:
 * date: 2022/7/17 23:09
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class KeyUtils implements InitializingBean {

    @Value("${aliyun.sms.appcode}")
    private String appCode;

    public static String TEMPLATE_COMMON = "0001";

    public static String TEMPLATE_REGISTER = "0002";

    public static String TEMPLATE_LOGIN = "0003";

    public static String APP_CODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_CODE = appCode;
    }
}
