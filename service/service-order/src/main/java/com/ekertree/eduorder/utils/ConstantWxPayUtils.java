package com.ekertree.eduorder.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: ConstantWxPayUtils
 * Description:
 * date: 2022/7/25 15:31
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class ConstantWxPayUtils implements InitializingBean {

    @Value("${wx.pay.appid}")
    private String appid;
    @Value("${wx.pay.partner}")
    private String partner;
    @Value("${wx.pay.key}")
    private String partnerKey;
    @Value("${wx.pay.notifyurl}")
    private String notifyUrl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appid;
        PARTNER = partner;
        PARTNER_KEY = partnerKey;
        NOTIFY_URL = notifyUrl;
    }
}
