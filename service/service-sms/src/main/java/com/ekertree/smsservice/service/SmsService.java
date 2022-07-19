package com.ekertree.smsservice.service;

import org.apache.http.HttpResponse;

/**
 * ClassName: SmsService
 * Description:
 * date: 2022/7/17 21:47
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public interface SmsService {
    boolean sendCommonCode(String phone);

    boolean sendLoginCode(String phone);

    boolean sendRegisterCode(String phone);
}
