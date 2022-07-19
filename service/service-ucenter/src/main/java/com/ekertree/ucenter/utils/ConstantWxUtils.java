package com.ekertree.ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName: ConstantWxUtils
 * Description:
 * date: 2022/7/19 16:24
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class ConstantWxUtils implements InitializingBean {

    @Value("${wx.open.appid}")
    private String appId;
    @Value("${wx.open.appsecret}")
    private String appSecret;
    @Value("${wx.open.redirecturl}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
