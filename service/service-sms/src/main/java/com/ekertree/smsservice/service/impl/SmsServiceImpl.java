package com.ekertree.smsservice.service.impl;

import com.ekertree.smsservice.service.SmsService;
import com.ekertree.smsservice.utils.KeyUtils;
import com.ekertree.smsservice.utils.SendUtils;
import org.apache.http.HttpResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName: SmsServiceImpl
 * Description:
 * date: 2022/7/17 21:47
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Service
public class SmsServiceImpl implements SmsService {

    private RedisTemplate<String,String> redisTemplate;

    public SmsServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean sendCommonCode(String phone) {
        HttpResponse httpResponse = SendUtils.sendCode(redisTemplate, phone, KeyUtils.TEMPLATE_COMMON);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean sendLoginCode(String phone) {
        HttpResponse httpResponse = SendUtils.sendCode(redisTemplate, phone, KeyUtils.TEMPLATE_LOGIN);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean sendRegisterCode(String phone) {
        HttpResponse httpResponse = SendUtils.sendCode(redisTemplate, phone, KeyUtils.TEMPLATE_REGISTER);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            return true;
        }else {
            return false;
        }
    }
}
