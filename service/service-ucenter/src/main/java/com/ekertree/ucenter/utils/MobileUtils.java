package com.ekertree.ucenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: MobileUtils
 * Description:
 * date: 2022/7/18 15:21
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class MobileUtils {
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0|5|6|7|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
