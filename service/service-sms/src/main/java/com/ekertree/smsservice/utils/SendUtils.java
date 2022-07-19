package com.ekertree.smsservice.utils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SendUtils
 * Description:
 * date: 2022/7/18 14:05
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class SendUtils {
    public static HttpResponse sendCode(RedisTemplate<String,String> redisTemplate, String phone, String template) {
        //生成随机六位验证码
        String code;
        //从redis中获取验证码
        String storeCode = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(storeCode)) {
            code = storeCode;
        } else {
            code = RandomUtil.getSixBitRandom();
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        }
        String host = "https://miitangs09.market.alicloudapi.com";
        String path = "/v1/tools/sms/code/sender";
        String method = "POST";
        String appcode = KeyUtils.APP_CODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        //需要给X-Ca-Nonce的值生成随机字符串，每次请求不能相同
        headers.put("X-Ca-Nonce", UUID.randomUUID().toString());
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("filterVirtual", "false");
        bodys.put("phoneNumber", phone);
        bodys.put("reqNo", "miitangtest01");
        bodys.put("smsSignId", "0000");
        bodys.put("smsTemplateNo", template);
        bodys.put("verifyCode", code);
        HttpResponse response = null;
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
