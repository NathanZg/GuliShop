package com.ekertree.ucenter.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.commonutils.JwtUtils;
import com.ekertree.commonutils.MD5;
import com.ekertree.servicebase.excetionhandler.GuliException;
import com.ekertree.ucenter.entity.Member;
import com.ekertree.ucenter.entity.vo.LoginVo;
import com.ekertree.ucenter.entity.vo.RegisterVo;
import com.ekertree.ucenter.mapper.MemberMapper;
import com.ekertree.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.ucenter.utils.ConstantWxUtils;
import com.ekertree.ucenter.utils.MobileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.security.rsa.RSASignature;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    private RedisTemplate<String, String> redisTemplate;

    public MemberServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String login(LoginVo loginVo) {
        //获取登陆手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "账号或密码不能为空！");
        }
        //判断手机号格式是否正确
        if (!MobileUtils.isMobile(mobile)) {
            throw new GuliException(20001, "手机号格式错误！");
        }
        //判断手机号是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        //查询手机号是否存在
        if (mobileMember == null) {
            throw new GuliException(20001, "手机号不存在！");
        }
        //查询密码是否正确
        //MD5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码错误！");
        }
        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "账户已被封禁！");
        }
        //登陆成功
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空判断
        if (StringUtils.isEmpty(code) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "注册失败！");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (StringUtils.isEmpty(redisCode)) {
            throw new GuliException(20001, "验证码已过期！");
        }
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误！");
        }
        //判断手机号是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "账号已经被注册！");
        }
        //添加到数据库
        Member member = new Member();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("https://www.mp3pc.com/uploads/fc575927b363b932b8874f58ae18c7b4.jpg");
        baseMapper.insert(member);
    }

    @Override
    public String redirectUrl() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //redirect_uri进行需要进行UrlEncode
        String redirect_uri = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        String encode_redirect_uri = null;
        try {
            encode_redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                encode_redirect_uri,
                "ekertree");
        return url;
    }

    @Override
    public String callback(String code, String state) {
        //获取临时票据 code值
        //拿临时票据请求微信固定地址，得到access_token 和 openid
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);
        //获取返回值
        String accessTokenInfo = HttpUtil.get(accessTokenUrl);
        //解析JSON数据
        JSONObject accessTokenInfoJson = JSONUtil.parseObj(accessTokenInfo);
        String accessToken = (String) accessTokenInfoJson.get("access_token");
        String openid = (String) accessTokenInfoJson.get("openid");

        //拿access_token和openid 再去请求微信的固定地址 获取到扫描人的信息
        //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
        String userInfo = HttpUtil.get(userInfoUrl);
        //解析JSON数据
        JSONObject userInfoJson = JSONUtil.parseObj(userInfo);
        String nickname = (String) userInfoJson.get("nickname");
        String headimgurl = (String) userInfoJson.get("headimgurl");

        //判断数据库中是否存在该openid
        Member member = getMemberByOpenId(openid);
        if (member == null){
            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            baseMapper.insert(member);
        }
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public Member getMemberByOpenId(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
