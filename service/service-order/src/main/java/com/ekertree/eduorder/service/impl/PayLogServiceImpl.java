package com.ekertree.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.eduorder.entity.Order;
import com.ekertree.eduorder.entity.PayLog;
import com.ekertree.eduorder.mapper.PayLogMapper;
import com.ekertree.eduorder.service.OrderService;
import com.ekertree.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.eduorder.utils.ConstantWxPayUtils;
import com.ekertree.eduorder.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-24
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    private OrderService orderService;

    public PayLogServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Map<String, Object> createNative(String orderNo) {
        //根据课程号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        //使用map设置生成二维码需要的参数
        HashMap<String, String> m = new HashMap<>();
        m.put("appid", ConstantWxPayUtils.APP_ID);
        m.put("mch_id", ConstantWxPayUtils.PARTNER);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", ConstantWxPayUtils.NOTIFY_URL + "\n");
        m.put("trade_type", "NATIVE");
        //发送请求
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        String xml;
        Map<String, String> resultMap = null;
        try {
            httpClient.setHttps(true);
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m, ConstantWxPayUtils.PARTNER_KEY));
            httpClient.post();
            //返回xml格式的内容
            xml = httpClient.getContent();
            //将xml格式转换成map集合
            resultMap = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //封装返回的结果集
        Map map = new HashMap<>();
        map.put("out_trade_no", orderNo);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", resultMap.get("result_code"));
        map.put("code_url", resultMap.get("code_url"));
        return map;
    }

    @Override
    public Map<String, String> queryOrderStatus(String orderNo) {
        //1、封装参数
        Map m = new HashMap<>();
        m.put("appid", ConstantWxPayUtils.APP_ID);
        m.put("mch_id", ConstantWxPayUtils.PARTNER);
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        String xml;
        Map<String, String> resultMap = null;
        try {
            //2、设置请求
            HttpClient client = new
                    HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    ConstantWxPayUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            xml = client.getContent();
            resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        if(order.getStatus().intValue() == 1)
            return;
        order.setStatus(1);
        orderService.updateById(order);
        //记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
